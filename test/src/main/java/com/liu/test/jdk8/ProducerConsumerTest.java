package com.liu.test.jdk8;

import java.util.ArrayDeque;
import java.util.Queue;

public class ProducerConsumerTest {
    public static void main(String[] args) {
        int maxSize = 100;
        Queue<Integer> queue = new ArrayDeque<>();
        Integer aucrement = 0;
        Thread producer = new Thread(() ->{
            while(true){
                synchronized (queue){
                    while(queue.size() == maxSize){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    queue.add(aucrement);
                    queue.notifyAll();
                }
            }

        });

        Thread consumer = new Thread(() ->{
            while(true){
                synchronized (queue){
                    while(queue.size() == 0){
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Integer poll = queue.poll();
                    System.out.println("poll:"+poll);
                    queue.notifyAll();
                }
            }

        });
        producer.start();
        consumer.start();
    }
}
