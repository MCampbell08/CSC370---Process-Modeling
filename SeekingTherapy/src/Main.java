import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main {

    private static final int MIN_CUST_AMOUNT = 1;
    private static final int MAX_CUST_AMOUNT = 3;
    private static final int MIN_CUST_ARRIVAL_TIME = 5;
    private static final int MAX_CUST_ARRIVAL_TIME = 20;

    public static void main(String[] args) throws InterruptedException {
        List<MyThread> customers = new ArrayList<>();
        long openingTime = System.currentTimeMillis();

        while(System.currentTimeMillis()-openingTime < 120000){
            //if(!customers.isEmpty()) Thread.sleep(MyThread.rand.nextInt(MAX_CUST_ARRIVAL_TIME - MIN_CUST_ARRIVAL_TIME + 1) + MIN_CUST_ARRIVAL_TIME);
            int amountCustom = MyThread.rand.nextInt(MAX_CUST_AMOUNT - MIN_CUST_AMOUNT + 1) + MIN_CUST_AMOUNT;
            System.out.println("Number of patients: " + amountCustom);
            for(int i = 0; i < amountCustom; i++){
                MyThread currCustomer = new MyThread();
                currCustomer.start();
                customers.add(currCustomer);
            }
            for (MyThread currCustomer : customers){
                currCustomer.join();
            }
        }
    }
}
