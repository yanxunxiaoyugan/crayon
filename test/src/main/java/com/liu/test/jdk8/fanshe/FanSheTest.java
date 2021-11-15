package com.liu.test.jdk8.fanshe;

import java.lang.reflect.Method;

public class FanSheTest {
    public static void main(String[] args) {
        Son son = new Son();
        Class<? extends Son> sonClass = son.getClass();
        Method[] declaredMethods = sonClass.getDeclaredMethods();
        Method[] methods = sonClass.getMethods();
        System.out.println(declaredMethods);
        System.out.println(methods);

    }
}
