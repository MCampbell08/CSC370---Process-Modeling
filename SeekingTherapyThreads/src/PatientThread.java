import java.util.Random;

public class PatientThread extends Thread{
    public static int therapist;

    private static Object lock = new Object();

    private static final int MIN_CUST_TIME = 5000;
    private static final int MAX_CUST_TIME = 15000;

    private long patientTimeStart = 0;

    static Random rand = new Random();

    public PatientThread(){
        patientTimeStart = System.currentTimeMillis();
    }

    @Override
    public void run() {
        synchronized (lock) {
            int local = therapist;
            int pauseAmount = rand.nextInt(MAX_CUST_TIME - MIN_CUST_TIME + 1) + MIN_CUST_TIME;
            pause(pauseAmount);
            local++;
            therapist = local;
            System.out.println("Patient: " + this.getId() + " | Session time (millis): " + (pauseAmount));
        }
    }

    private void pause(long m){
        try{
            sleep(m);
        }
        catch (InterruptedException ex){}
    }
}
