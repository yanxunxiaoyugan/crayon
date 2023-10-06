package com.liu.blog.config;

import com.liu.blog.controller.BlogController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TestBeanPostProcessor implements BeanPostProcessor {

    public TestBeanPostProcessor(){
        System.out.println("TestBeanPostProcessor");
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("blogController")){
            System.out.println("postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals( "blogController")){
            System.out.println("postProcessBeforeInitialization");
        }
        return bean;
    }
}
