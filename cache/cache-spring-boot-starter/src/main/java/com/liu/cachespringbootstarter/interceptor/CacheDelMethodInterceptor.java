package com.liu.cachespringbootstarter.interceptor;

import com.liu.cachespringbootstarter.annotation.CacheDel;
import com.liu.cachespringbootstarter.handler.CacheHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public class CacheDelMethodInterceptor implements MethodInterceptor {

    CacheHandler cacheHandler;

    public CacheDelMethodInterceptor(CacheHandler cacheHandler) {
        this.cacheHandler = cacheHandler;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        //执行被拦截的方法
        Object result = methodInvocation.proceed();
        if (method.isAnnotationPresent(CacheDel.class)) {
            CacheDel cacheDel = method.getAnnotation(CacheDel.class);
            //删除缓存
            cacheHandler.cacheDel(cacheDel);
        }
        return null;
    }
}
