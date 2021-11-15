package com.liu.test.springtest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
//@Component
public class LifeCycle implements InitializingBean, BeanPostProcessor {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("initializing.....");
    }

//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("postProcessBeforeInitialization.....");
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("postProcessAfterInitialization.....");
//        return bean;
//    }

    @PostConstruct
    public void postCon(){
        System.out.println("postCon");
    }
    public void init(){
        System.out.println("init");
    }
}
