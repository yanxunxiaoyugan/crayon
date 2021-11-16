package com.liu.test.springtest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController implements org.springframework.context.ApplicationContextAware {
    @Autowired
    private AsyncTest asyncTest;
    private ApplicationContext applicationContext;
    @GetMapping("/async1")
    public String async1(String as){
        AsyncTest bean = applicationContext.getBean(AsyncTest.class);
        System.out.println(bean);
        asyncTest.async1();
        return "asdf";
    }

    @GetMapping("/async2")
    public void async2(ApplicationContext applicationContext){
        asyncTest.async2();
    }

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
