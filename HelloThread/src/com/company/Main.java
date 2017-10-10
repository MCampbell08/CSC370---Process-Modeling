package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MyThread.x = 0;

        List<MyThread> threads = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            MyThread t = new MyThread();
            t.start();
            threads.add(t);
        }

        for(MyThread thread : threads){
            thread.join();
        }

    }
}
