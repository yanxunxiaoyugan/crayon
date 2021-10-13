package com.liu.test.jdk8;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class AqsTest {
    public static void main(String[] args) {


        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);


        ReentrantLock lock = new ReentrantLock(true);
        try{
            lock.lock();
            lock.lock();
            Thread thread = new Thread(() ->{
                lock.lock();
            });
            thread.start();
        }finally {
//            lock.unlock();
//            lock.unlock();
        }
        boolean locked = lock.isLocked();
        System.out.println(locked);
    }
}
