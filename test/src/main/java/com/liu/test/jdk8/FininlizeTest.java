package com.liu.test.jdk8;

public class FininlizeTest {
    public static void main(String[] args) {
        FinalTest test = new FinalTest();
        test = null;
        System.gc();
    }
}
