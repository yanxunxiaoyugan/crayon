package com.liu.test.jdk8.dynamic;

import java.lang.reflect.Proxy;

public class DynamicProxyTest {
    public static void main(String[] args) {
        HelloService helloService =(HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(), new Class[]{HelloService.class}, new HelloProxy(new HelloServiceImpl()));
        helloService.hi();

    }
}
