import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadList extends Thread{
    public static List<MyThread> patients = new ArrayList<>();
    public static List<MyThread> patientsSeen = new ArrayList<>();

    public static boolean closingTime = false;
    public static boolean switchingPatients = true;

    private static final int MIN_CUST_AMOUNT = 1;
    private static final int MAX_CUST_AMOUNT = 3;

    private static Object lock = new Object();

    private static final int MIN_CUST_ARRIVAL_TIME = 5000;
    private static final int MAX_CUST_ARRIVAL_TIME = 20000;

    static Random rand = new Random();

    private boolean openingTime = true;

    @Override
    public void run(){
        //synchronized (lock){
            long patientStartInterval = System.currentTimeMillis();
            long timeWaitInterval = rand.nextInt(MAX_CUST_ARRIVAL_TIME - MIN_CUST_ARRIVAL_TIME + 1) + MIN_CUST_ARRIVAL_TIME;
            while(!closingTime) {
                if (System.currentTimeMillis() - patientStartInterval > timeWaitInterval || openingTime) {
                    int amountCustom = MyThread.rand.nextInt(MAX_CUST_AMOUNT - MIN_CUST_AMOUNT + 1) + MIN_CUST_AMOUNT;
                    if(switchingPatients){
                        for (int i = 0; i < amountCustom; i++) {
                            MyThread t = new MyThread();
                            patients.add(t);
                            System.out.println("Patient (" + t.getId() + "), is waiting to see the therapist.");
                        }
                        switchingPatients = false;
                    }

                    patientStartInterval = System.currentTimeMillis();
                    timeWaitInterval = rand.nextInt(MAX_CUST_ARRIVAL_TIME - MIN_CUST_ARRIVAL_TIME + 1) + MIN_CUST_ARRIVAL_TIME;
                }
                openingTime = false;
            }
        //}
    }
}
