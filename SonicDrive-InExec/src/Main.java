import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;

public class Main {

    private static final int CUSTOMER_AMOUNT = 10;
    public static int x = 0;

    public static final int PARKING_SPOTS = 5;
    public static final int EMPLOYEE_AMOUNT = 3;
    private static final int MIN_CUST_TIME = 300;
    private static final int MAX_CUST_TIME = 500;

    private static Semaphore semaphoreParking = new Semaphore(PARKING_SPOTS);
    private static Semaphore semaphoreEmployee = new Semaphore(EMPLOYEE_AMOUNT);
    private static ExecutorService svc = Executors.newFixedThreadPool(PARKING_SPOTS);

    private static Object lock = new Object();

    private static Random gen = new Random();
    public static void main(String[] args) {
        x = 0;

        for(int i = 0; i < CUSTOMER_AMOUNT; i++){
            svc.submit(() -> {
                try {
                    semaphoreParking.acquire();
                }
                catch (InterruptedException ex){throw new RuntimeException(ex);}
                try {
                    System.out.println("Customer " + Thread.currentThread().getName() + " has taken a parking spot.");
                    takeOrder();
                    deliverFood();
                    int local = x;
                    System.out.println("Food is being eaten by: " + Thread.currentThread().getName());
                    pause(gen.nextInt(MAX_CUST_TIME - MIN_CUST_TIME + 1) + MIN_CUST_TIME);
                    local++;
                    x = local;
                }
                finally {
                    semaphoreParking.release();
                    System.out.println("Customer " + Thread.currentThread().getName() + " has released a parking spot.");
                }
            });
        }
    }
    private static void takeOrder(){

        try {
            semaphoreEmployee.acquire();
        }
        catch (InterruptedException ex){throw new RuntimeException(ex);}

        try {
            System.out.println("Taking order of Customer : " + Thread.currentThread().getName());
            pause(gen.nextInt(MAX_CUST_TIME - MIN_CUST_TIME + 1) + MIN_CUST_TIME);
            synchronized (lock) {
                System.out.println("Taking payment of Customer: " + Thread.currentThread().getName());
                int local = x;
                pause(gen.nextInt(MAX_CUST_TIME - MIN_CUST_TIME + 1) + MIN_CUST_TIME);
                local++;
                x = local;
                System.out.println("Finishing payment of Customer: " + Thread.currentThread().getName());
            }
        }
        finally {
            semaphoreEmployee.release();
        }
    }

    private static void deliverFood(){
        try{
            semaphoreEmployee.acquire();
        }
        catch (InterruptedException ex){throw new RuntimeException(ex);}
        try{
            System.out.println("Delivering order to Customer: " + Thread.currentThread().getName());
            pause(gen.nextInt(MAX_CUST_TIME - MIN_CUST_TIME + 1) + MIN_CUST_TIME);
        }
        finally {
            semaphoreEmployee.release();
        }
    }

    private static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
