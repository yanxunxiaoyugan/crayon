package com.liu.test.jdk8;

public class RunnerTask implements Runnable{

    @Override
    public void run() {
        System.out.println("123:"+Thread.currentThread().getName());
    }
}
