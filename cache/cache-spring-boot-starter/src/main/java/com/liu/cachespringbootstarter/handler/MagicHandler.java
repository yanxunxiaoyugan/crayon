package com.liu.cachespringbootstarter.handler;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.cache.redis.RedisCacheManager;
import com.liu.cachespringbootstarter.dto.CacheKeyDto;
import com.liu.cachespringbootstarter.paser.Ipaser;
import javafx.beans.binding.ObjectExpression;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Collection;

public class MagicHandler {
    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private Ipaser ipaser;

    public static boolean isMagic(CacheAble cacheAble, Method method) {
        //如果返回值是个数组或者collection 返回true，否则返回false
        Class<?> returnType = method.getReturnType();
        if (returnType.isArray() || Collection.class.isAssignableFrom(returnType)) {
            return true;
        }
        return false;
    }

    /**
     * 处理magic模式
     *
     * @param able
     * @param methodInvocation
     */
    public Object handler(CacheAble able, MethodInvocation methodInvocation) throws Throwable {
        if (methodInvocation.getArguments().length == 0) {
            //如果该方法没有参数 那么直接处理
            Object returnValue = methodInvocation.proceed();
            this.doHandler(returnValue,able,methodInvocation);
            return returnValue;
        }
        return null;

    }

    /**
     *
     */
    public void doHandler(Object returnValue,CacheAble able, MethodInvocation methodInvocation) {
        if (returnValue == null) {
            return;
        }
        //如果返回值是数组
        if (returnValue.getClass().isArray()) {
            Object[] returnValueArray = (Object[]) returnValue;
            for (Object object : returnValueArray) {
                CacheKeyDto cacheMagicKey = ipaser.getCacheMagicKey(methodInvocation, able.magic(), object);
                if(redisCacheManager.get(cacheMagicKey.getKey()) == null){
                    //判断缓存是是否存在此key
                    redisCacheManager.put(cacheMagicKey.getKey(),cacheMagicKey.getCacheObject(),able.expire(),able.timeUnit());
                }
            }

        } else if (Collection.class.isAssignableFrom(returnValue.getClass())) {
            Collection<Object> returnValueCollection = (Collection<Object>) returnValue;
            for (Object object : returnValueCollection) {
                CacheKeyDto cacheMagicKey = ipaser.getCacheMagicKey(methodInvocation, able.magic(), object);
                if(redisCacheManager.get(cacheMagicKey.getKey()) == null){
                    //判断缓存是是否存在此key
                    redisCacheManager.put(cacheMagicKey.getKey(),cacheMagicKey.getCacheObject(),able.expire(),able.timeUnit());
                }
            }
        }
    }
}
