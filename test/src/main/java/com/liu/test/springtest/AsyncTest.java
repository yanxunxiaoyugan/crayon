package com.liu.test.springtest;

import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTest {
    public void async1(){
        System.out.println("1:"+Thread.currentThread().getName());
        this.async2();
        ((AsyncTest) AopContext.currentProxy()).async2();
    }
    @Async
    public void async2(){
        System.out.println("2:"+Thread.currentThread().getName());
    }
}
