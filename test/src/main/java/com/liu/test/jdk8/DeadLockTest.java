package com.liu.test.jdk8;

import java.util.concurrent.TimeUnit;

public class DeadLockTest {
    public static void main(String[] args) {
        Object resourceA = new Object();
        Object resourceB = new Object();

        Runnable target;
        Thread thread1 = new Thread( () ->{
            synchronized (resourceA){
                try {
                    System.out.println("thread1 get ResourceA lock");
                    TimeUnit.SECONDS.sleep(100);
                    synchronized (resourceB){
                        TimeUnit.SECONDS.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread( () ->{
            synchronized (resourceB){
                try {
                    System.out.println("thread2 get ResourceB lock");
                    TimeUnit.SECONDS.sleep(100);
                    synchronized (resourceA){
                        TimeUnit.SECONDS.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();

    }
}
