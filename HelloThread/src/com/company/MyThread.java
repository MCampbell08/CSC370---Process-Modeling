package com.company;

import java.util.Random;

public class MyThread extends Thread {

    public static int x;

//    private static Object lock = new Object();

//    static Random gen = new Random();

    @Override
    public void run() {
//        synchronized (lock) {
            int local = x;
//            pause(gen.nextInt(500) + 250);
            local++;
            x = local;
            System.out.print(x);
//        }
    }

//    private void pause(long m){
//        try{
//            sleep(m);
//        }
//        catch (InterruptedException ex){}
//    }
}
