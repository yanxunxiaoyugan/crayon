package com.liu.test.jdk8;

import org.openjdk.jol.info.ClassLayout;

public class SynchronizedTest {
//    static FinalTest a = new FinalTest();
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10000);
        FinalTest a = new FinalTest();
        System.out.println("main:"+ClassLayout.parseInstance(a).toPrintable());
        Thread.sleep(1000);
        synchronized (a){
            System.out.println(ClassLayout.parseInstance(a).toPrintable());
//            a.hashCode();
//            System.out.println("main:"+ClassLayout.parseInstance(a).toPrintable());
            synchronized (a){
//                System.gc();
                System.out.println(ClassLayout.parseInstance(a).toPrintable());
                synchronized (a){
                    System.out.println(ClassLayout.parseInstance(a).toPrintable());
                }
            }
        }

        System.out.println("main1:"+ClassLayout.parseInstance(a).toPrintable());
        Runnable target;
//        new Thread(() ->{
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            synchronized (a){
//                System.out.println("thread:"+ClassLayout.parseInstance(a).toPrintable());
////                a.hashCode();
//                System.out.println("thread:"+ClassLayout.parseInstance(a).toPrintable());
//            }
//        }).start();
//        a.hashCode();
//        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
    static void test1(){

    }

}
