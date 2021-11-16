package com.liu.test.springtest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
    @Before("execution(* com.liu.test.springtest.AsyncController.*(..))")
    public void before(){
        System.out.println("asdf");
    }
}
