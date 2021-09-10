package com.liu.agent.study.demo1;

import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader  extends URLClassLoader {

    public MyClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> aClass = findClass("cn.bintools.cloudquery.module.mariadb.execute.MariaDbRoutineExecuteFactory");
        System.out.println(aClass);
        return super.loadClass(name);
    }
}
