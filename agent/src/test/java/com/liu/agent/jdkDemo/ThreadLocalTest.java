package com.liu.agent.jdkDemo;

import sun.misc.Unsafe;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;
public class ThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {

        URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        for(URL url : systemClassLoader.getURLs()){
            System.out.println(url);
        }
        System.out.println("=======================================================================================");
        System.out.println(Unsafe.class.getClassLoader());
        System.out.println(Thread.currentThread().getContextClassLoader());
        Thread.currentThread().setContextClassLoader(Unsafe.class.getClassLoader());
        HashMapDemo hashMapDemo = new HashMapDemo();
        WeakReference<HashMapDemo> test = new WeakReference<>(hashMapDemo);

        System.out.println("classLoad:"+hashMapDemo.getClass().getClassLoader());
        System.out.println("before clear:"+test.get());
        String[] sb = new String[100];
        for(int i=0 ; i < 1000; i++){
            sb = new String[100];
//            System.out.println(sb.length);
        }

        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        TimeUnit.SECONDS.sleep(5);
        if(test.get() == null){
            System.out.println("cleasr");
        }
        System.out.println(hashMapDemo);
    }
}
