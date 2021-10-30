package com.liu.test.jdk8;

public class FinalTest {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("asdf");
    }

}
