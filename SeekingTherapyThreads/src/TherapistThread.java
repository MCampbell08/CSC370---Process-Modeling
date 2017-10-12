import java.util.Random;

public class TherapistThread extends Thread{

    private static final int MIN_CUST_AMOUNT = 1;
    private static final int MAX_CUST_AMOUNT = 3;
    private static final int MIN_CUST_ARRIVAL_TIME = 5000;
    private static final int MAX_CUST_ARRIVAL_TIME = 20000;

    public static boolean closingTime = false;


    static Random rand = new Random();


    @Override
    public void run(){
        long patientStartInterval = System.currentTimeMillis();
        long timeWaitInterval = rand.nextInt(MAX_CUST_ARRIVAL_TIME - MIN_CUST_ARRIVAL_TIME + 1) + MIN_CUST_ARRIVAL_TIME;
        while (!closingTime) {
            if(System.currentTimeMillis() - patientStartInterval > timeWaitInterval) {
                int amountCustom = rand.nextInt(MAX_CUST_AMOUNT - MIN_CUST_AMOUNT + 1) + MIN_CUST_AMOUNT;
                for(int i = 0; i < amountCustom; i++){
                    PatientThread t = new PatientThread();
                    System.out.println("Patient (" + t.getId() + "), has entered the facility.");
                }
                patientStartInterval = System.currentTimeMillis();
                timeWaitInterval = rand.nextInt(MAX_CUST_ARRIVAL_TIME - MIN_CUST_ARRIVAL_TIME + 1) + MIN_CUST_ARRIVAL_TIME;
            }
        }
    }
}
