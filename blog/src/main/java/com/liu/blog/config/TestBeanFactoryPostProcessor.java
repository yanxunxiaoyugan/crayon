package com.liu.blog.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    public TestBeanFactoryPostProcessor(){
        System.out.println("TestBeanFactoryPostProcessor");
    }
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("beanFactoyr........");
    }
}
