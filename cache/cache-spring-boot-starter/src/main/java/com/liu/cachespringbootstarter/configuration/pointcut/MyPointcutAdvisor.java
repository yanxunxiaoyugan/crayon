package com.liu.cachespringbootstarter.configuration.pointcut;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

import java.lang.annotation.Annotation;

public class MyPointcutAdvisor extends DefaultPointcutAdvisor {

    /**
     * 对所有的methodAnnotationType进行增强
     * @param methodAnnotationType
     * @param advice
     */
    public MyPointcutAdvisor(Class<? extends Annotation> methodAnnotationType, Advice advice) {

        Pointcut pointcut = new AnnotationMatchingPointcut(null, methodAnnotationType);
        this.setPointcut(pointcut);
        this.setAdvice(advice);
    }
}
