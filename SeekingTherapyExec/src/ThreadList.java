import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ThreadList extends Thread{
    public static List<Thread> patients = new ArrayList<>();
    public static List<Thread> patientsSeen = new ArrayList<>();
    public static HashMap<Long, Long> patientsStartTime = new HashMap();
    public static boolean closingTime = false;
    public static int therapist = 1;
    public static long patientTimeStart = 0;

    private static final int MIN_CUST_AMOUNT = 1;
    private static final int MAX_CUST_AMOUNT = 3;
    private static final int MIN_CUST_ARRIVAL_TIME = 5000;
    private static final int MAX_CUST_ARRIVAL_TIME = 20000;
    private static final int MIN_CUST_TIME = 5000;
    private static final int MAX_CUST_TIME = 15000;

    public static Semaphore semaphore = new Semaphore(1);

    private static ExecutorService svc = Executors.newFixedThreadPool(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            patientTimeStart = System.currentTimeMillis();
            patientsStartTime.put(t.getId(), patientTimeStart);
            return t;
        }
    });
    private static Random rand = new Random();

    private boolean openingTime = true;

    @Override
    public void run(){
        long patientStartInterval = System.currentTimeMillis();
        long timeWaitInterval = rand.nextInt(MAX_CUST_ARRIVAL_TIME - MIN_CUST_ARRIVAL_TIME + 1) + MIN_CUST_ARRIVAL_TIME;
        while(!closingTime) {
            if (System.currentTimeMillis() - patientStartInterval > timeWaitInterval || openingTime) {
                int amountCustom = rand.nextInt(MAX_CUST_AMOUNT - MIN_CUST_AMOUNT + 1) + MIN_CUST_AMOUNT;
                for (int i = 0; i < amountCustom; i++) {
                    svc.submit(() -> {
                        try {
                            ThreadList.semaphore.acquire();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            int local = therapist;
                            int pauseAmount = rand.nextInt(MAX_CUST_TIME - MIN_CUST_TIME + 1) + MIN_CUST_TIME;
                            pause(pauseAmount);
                            local++;
                            therapist = local;
                            System.out.println("Patient: " + patients.get(therapist).getId() + " | Session time (millis): " + (pauseAmount));
                            patients.add(Thread.currentThread());
                        } finally {
                            ThreadList.semaphore.release();
                        }
                    });
                    if (patients.size() != 0) {
                        System.out.println("Patient (" + patients.get(therapist).getId() + "), is waiting to see the therapist.");
                    }
                }
                patientStartInterval = System.currentTimeMillis();
                timeWaitInterval = rand.nextInt(MAX_CUST_ARRIVAL_TIME - MIN_CUST_ARRIVAL_TIME + 1) + MIN_CUST_ARRIVAL_TIME;
            }
            openingTime = false;
        }
    }

    private void pause(long m){
        try{
            sleep(m);
        }
        catch (InterruptedException ex){}
    }

    public long getPatientTimeStart() {
        return patientTimeStart;
    }

}
