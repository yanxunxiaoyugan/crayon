package com.liu.test.jdk8;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class AqsTest {
    public static void main(String[] args) {
        Thread main = Thread.currentThread();
        ReentrantLock lock = new ReentrantLock();
        try{
            lock.lock();
            Thread thread = new Thread(() ->{
                try {
                    main.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
            });
            thread.start();
//            thread.join();
            Thread thread1 = new Thread(() ->{
                try {
                    main.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
            });
            thread1.start();
//            thread1.join();
//            lock.lock();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            lock.unlock();
//            lock.unlock();
        }
        boolean locked = lock.isLocked();
        System.out.println(locked);
    }
}
