package com.liu.test.springtest;

import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import org.aspectj.lang.annotation.Aspect;
@Component
@Aspect
public class AspectJBeanPostProcessor {

    @Before("execution(* com.liu.test.springtest.*.*(..))")
    public void before(){
        System.out.println("asdf");
    }
}
