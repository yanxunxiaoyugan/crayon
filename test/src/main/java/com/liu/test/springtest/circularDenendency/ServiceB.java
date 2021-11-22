package com.liu.test.springtest.circularDenendency;

import org.springframework.stereotype.Component;

@Component
public class ServiceB {
    private ServiceA serviceA;
}
