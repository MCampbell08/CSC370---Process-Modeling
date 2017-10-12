import java.util.Random;
import java.util.concurrent.Semaphore;

public class Employees extends Thread{public static int x = 0;

    public static final int EMPLOYEES_COUNT = 8;

    private static Semaphore semaphore = new Semaphore(EMPLOYEES_COUNT);

    private Random gen = new Random();

    @Override
    public void run(){
        try {
            semaphore.acquire();
        }
        catch (InterruptedException ex){throw new RuntimeException(ex);}

        try {
            int local = x;
            pause(gen.nextInt(550) + 250);
            local++;
            x = local;
        }
        finally {
            semaphore.release();
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
