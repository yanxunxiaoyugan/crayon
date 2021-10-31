package com.liu.test.jdk8;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class AqsTest {
    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock(true);
        try{
            lock.lock();
            Thread thread = new Thread(() ->{
                lock.lock();
            });
            thread.start();

            Thread thread1 = new Thread(() ->{
                lock.lock();
            });
            thread1.start();

            lock.lock();


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
