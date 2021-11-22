package com.liu.test.springtest.circularDenendency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceA {
    @Autowired
    ServiceB serviceB;
}
