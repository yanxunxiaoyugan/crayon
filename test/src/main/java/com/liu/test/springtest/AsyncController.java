package com.liu.test.springtest;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {
    @Autowired
    private AsyncTest asyncTest;
    @GetMapping("/async1")
    public void async1(){
        asyncTest.async1();
    }

    @GetMapping("/async2")
    public void async2(ApplicationContext applicationContext){
        String contextPath = applicationContext.getContextPath();
        asyncTest.async2();
    }

}
