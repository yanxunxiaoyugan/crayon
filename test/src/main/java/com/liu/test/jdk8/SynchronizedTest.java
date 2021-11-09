package com.liu.test.jdk8;

import org.openjdk.jol.info.ClassLayout;

public class SynchronizedTest {
    static FinalTest a = new FinalTest();
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);

        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        synchronized (a){
            System.out.println(ClassLayout.parseInstance(a).toPrintable());
            a.hashCode();
            System.out.println(ClassLayout.parseInstance(a).toPrintable());
        }
        Runnable target;
        new Thread(() ->{
            synchronized (a){
                System.out.println(ClassLayout.parseInstance(a).toPrintable());
                a.hashCode();
                System.out.println(ClassLayout.parseInstance(a).toPrintable());
            }
        }).start();
//        a.hashCode();
//        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
    static void test1(){

    }

}
