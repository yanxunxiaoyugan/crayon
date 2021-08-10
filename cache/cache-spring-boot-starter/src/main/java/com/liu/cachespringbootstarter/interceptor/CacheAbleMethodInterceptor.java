package com.liu.cachespringbootstarter.interceptor;


import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.handler.CacheHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @CacheAble注解的方法拦截器
 */
public class CacheAbleMethodInterceptor implements MethodInterceptor {
    private static  final Logger log = LoggerFactory.getLogger(CacheAbleMethodInterceptor.class);
    CacheHandler cacheHandler;
    public CacheAbleMethodInterceptor(CacheHandler cacheHandler){
        this.cacheHandler = cacheHandler;
    }
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        //拿到拦截的方法 如果方法被@CacheAble注解修饰
        if(method.isAnnotationPresent(CacheAble.class)){
            CacheAble cacheAble = method.getAnnotation(CacheAble.class);
            //解析key，condition
            //序列化value，加入缓存
            Object returnObj = cacheHandler.proceed(cacheAble,methodInvocation);
            return returnObj;
        }
        //不做处理 ，直接执行被代理的方法
        return methodInvocation.proceed();
    }


}
