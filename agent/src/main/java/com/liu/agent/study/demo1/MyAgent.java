package com.liu.agent.study.demo1;

import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyAgent {
    public static void main(String[] args) throws InterruptedException, MalformedURLException, ClassNotFoundException {
//        HashMapDemo hashMapDemo = new HashMapDemo();
        List<String> list = new ArrayList<>();
        list.add("file:/D:/workspace/code/cloudquery-base2/cloudquery-dms/ignore/dmc/dms_home/mariaDb/classes/");
        list.add("file:/D:/workspace/code/cloudquery-base2/cloudquery-dms/ignore/dmc/dms_home/mariaDb/deps/mysql-common-base2.0-1.0-SNAPSHOT.jar");
        URL url = new URL("file:/D:/workspace/code/cloudquery-base2/cloudquery-dms/ignore/dmc/dms_home/mariaDb/");
        URL url1 = new URL("file:/D:/workspace/code/cloudquery-base2/cloudquery-dms/ignore/dmc/dms_home/mariaDb/deps/mysql-common-base2.0-1.0-SNAPSHOT.jar");
        URL[] urls = new URL[2];
        urls[0] = url;
        urls[1] = url1;
        MyClassLoader myClassLoader = new MyClassLoader(urls,HashMapDemo.class.getClassLoader());
        Thread.currentThread().setContextClassLoader(myClassLoader);
        URL[] urLs = myClassLoader.getURLs();
        Class<?> aClass = myClassLoader.loadClass("cn.bintools.cloudquery.module.mariadb.execute.MariaDbRoutineExecuteFactory");
        System.out.println(aClass);
//        myClassLoader.f





        WeakReference<HashMapDemo> test = new WeakReference<>( new HashMapDemo());
        ClassLoader classLoader = test.getClass().getClassLoader();
        System.out.println(classLoader);
        System.out.println("before clear:"+test.get());
        String[] sb = new String[100];
        for(int i=0 ; i < 1000; i++){
            sb = new String[1000];
//            System.out.println(sb.length);
        }
        TimeUnit.SECONDS.sleep(5);
        if(test.get() == null){
            System.out.println("cleasr");
        }
        System.out.println("hashMapDemo");
    }
}
