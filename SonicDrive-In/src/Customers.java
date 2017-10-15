import java.util.Random;
import java.util.concurrent.Semaphore;

public class Customers extends Thread{

    public static int x = 0;

    public static final int PARKING_SPOTS = 5;
    public static final int EMPLOYEE_AMOUNT = 3;
    private static final int MIN_CUST_TIME = 300;
    private static final int MAX_CUST_TIME = 500;

    private static Semaphore semaphoreParking = new Semaphore(PARKING_SPOTS);
    private static Semaphore semaphoreEmployee = new Semaphore(EMPLOYEE_AMOUNT);

    private static Object lock = new Object();

    private Random gen = new Random();

    @Override
    public void run(){
        try {
            semaphoreParking.acquire();
        }
        catch (InterruptedException ex){throw new RuntimeException(ex);}

        try {
            System.out.println("Customer " + this.getName() + " has taken a parking spot.");
            takeOrder();
            deliverFood();
            int local = x;
            System.out.println("Food is being eaten by: " + this.getName());
            pause(gen.nextInt(MAX_CUST_TIME - MIN_CUST_TIME + 1) + MIN_CUST_TIME);
            local++;
            x = local;
        }
        finally {
            semaphoreParking.release();
            System.out.println("Customer " + this.getName() + " has released a parking spot.");
        }
    }

    private void takeOrder(){

        try {
            semaphoreEmployee.acquire();
        }
        catch (InterruptedException ex){throw new RuntimeException(ex);}

        try {
            System.out.println("Taking order of Customer : " + this.getName());
            pause(gen.nextInt(MAX_CUST_TIME - MIN_CUST_TIME + 1) + MIN_CUST_TIME);
            synchronized (lock) {
                System.out.println("Taking payment of Customer: " + this.getName());
                int local = x;
                pause(gen.nextInt(MAX_CUST_TIME - MIN_CUST_TIME + 1) + MIN_CUST_TIME);
                local++;
                x = local;
                System.out.println("Finishing payment of Customer: " + this.getName());
            }
        }
        finally {
            semaphoreEmployee.release();
        }
    }

    private void deliverFood(){
        try{
            semaphoreEmployee.acquire();
        }
        catch (InterruptedException ex){throw new RuntimeException(ex);}
        try{
            System.out.println("Delivering order to Customer: " + this.getName());
            pause(gen.nextInt(MAX_CUST_TIME - MIN_CUST_TIME + 1) + MIN_CUST_TIME);
        }
        finally {
            semaphoreEmployee.release();
        }
    }

    private void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
