package com.company;

import java.util.Random;
import java.util.concurrent.*;

public class Main {

    static final int COUNT_THREADS = 10;

    public static final int CRIT_THREADS_COUNT = 4;

    public static Semaphore semaphore = new Semaphore(CRIT_THREADS_COUNT);
    public static Random gen = new Random();

    static int x = 0;
    static ScheduledExecutorService svc = Executors.newScheduledThreadPool(5, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("Hello Thread");
            t.setPriority(7);
            t.setDaemon(true);
            return t;
        }
    });

    public static void main(String[] args) throws InterruptedException {

        x = 0;

        for(int i = 0; i < COUNT_THREADS; i++){
            svc.submit(() -> {
                try {
                    Main.semaphore.acquire();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    int local = Main.x;
                    pause(Main.gen.nextInt(500) + 250);
                    local++;
                    Main.x = local;
                } finally {
                    Main.semaphore.release();
                }
            });
        }

        System.out.println("Memory Value: " + x);
    }

    static void pause(long m){
        try{
            Thread.sleep(m);
        }
        catch (InterruptedException ex){}
    }
}
