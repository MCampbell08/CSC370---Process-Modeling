import java.util.Random;
import java.util.concurrent.Semaphore;

public class Customers extends Thread{

    public static int x = 0;

    public static final int PARKING_SPOTS = 3;

    private static Semaphore semaphore = new Semaphore(PARKING_SPOTS);

    private Random gen = new Random();

    @Override
    public void run(){
        try {
            semaphore.acquire();
        }
        catch (InterruptedException ex){throw new RuntimeException(ex);}

        try {
            System.out.println("Within Employee Class");
            int local = x;
            pause(gen.nextInt(500) + 250);
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
