package com.company;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class MyThread extends Thread {

    public static int x;

    public static final int CRIT_THREADS_COUNT = 4;

    //private static Object lock = new Object();

    private static Semaphore semaphore = new Semaphore(CRIT_THREADS_COUNT);

    static Random gen = new Random();

    @Override
    public void run() {

        try {
            semaphore.acquire();
        }
        catch (InterruptedException ex){throw new RuntimeException(ex);}

        try {
            int local = x;
            pause(gen.nextInt(500) + 250);
            local++;
            x = local;
        }
        finally {
            semaphore.release();
        }
//        synchronized (lock) {
//            int local = x;
//            pause(gen.nextInt(500) + 250);
//            local++;
//            x = local;
//            System.out.print(x);
//        }
    }

    private void pause(long m){
        try{
            sleep(m);
        }
        catch (InterruptedException ex){}
    }
}
