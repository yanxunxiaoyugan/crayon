package com.liu.test.jdk8;

import java.util.concurrent.*;

public class ExecutorsTest {
    public static void main(String[] args) {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue(100);
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1,2,1000, TimeUnit.SECONDS,blockingQueue);
//        executorService.execute(new RunnerTask());

    }
}
