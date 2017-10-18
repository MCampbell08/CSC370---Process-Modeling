import java.util.Random;
import java.util.concurrent.Semaphore;

public class MyThread extends Thread {

    public static int therapist = 0;
    public static Semaphore semaphore = new Semaphore(1);
    public static long patientTimeStart = 0;

    private static Random rand = new Random();

    private static final int MIN_CUST_TIME = 500;
    private static final int MAX_CUST_TIME = 1500;



    public MyThread(){
        patientTimeStart = System.currentTimeMillis();
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        if(ThreadList.closingTime){
            this.interrupt();
        }
        try {
            int local = therapist;
            int pauseAmount = rand.nextInt(MAX_CUST_TIME - MIN_CUST_TIME + 1) + MIN_CUST_TIME;
            pause(pauseAmount);
            local++;
            therapist = local;
            System.out.println("Patient: " + this.getId() + " | Session time (millis): " + (pauseAmount));
            ThreadList.patientsSeen.add(ThreadList.patients.get(ThreadList.patients.size() - 1));
        } finally {
            semaphore.release();
        }
        if(ThreadList.closingTime){
            this.interrupt();
        }
    }
    public long getPatientTimeStart() {
        return patientTimeStart;
    }

    private void pause(long m){
        try{
            sleep(m);
        }
        catch (InterruptedException ex){}
    }
}
