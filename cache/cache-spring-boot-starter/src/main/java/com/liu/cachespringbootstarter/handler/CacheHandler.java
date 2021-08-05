package com.liu.cachespringbootstarter.handler;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.cache.redis.RedisCacheManager;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Objects;

public class CacheHandler {
    RedisCacheManager redisCacheManager;
    public CacheHandler(RedisCacheManager redisCacheManager){
        this.redisCacheManager = redisCacheManager;
    }
    //处理缓存
    public Object proceed(CacheAble able, MethodInvocation methodInvocation) throws Throwable {
        Object dataInCache= redisCacheManager.get(able.key());
        if(Objects.nonNull(dataInCache)){
            return dataInCache;
        }
        // 执行代理的方法
        Object returnObj = methodInvocation.proceed();
        redisCacheManager.put(able.key(),returnObj);
        return returnObj;
        return returnObj;
    }
}
