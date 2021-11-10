package com.liu.test.jdk8;

public class PrintABC {
    volatile int status = 0;

    public void print(String content){
        System.out.println(content);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
