package com.liu.test.jdk8.dynamic;

public class HelloServiceImpl implements HelloService{
    @Override
    public final void hi() {
        System.out.println("hello impl");
    }
    public void h1(){
        System.out.println("asdf");
    }
}
