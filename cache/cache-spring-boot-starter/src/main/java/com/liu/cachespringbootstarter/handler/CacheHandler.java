package com.liu.cachespringbootstarter.handler;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.annotation.CacheDel;
import com.liu.cachespringbootstarter.cache.redis.RedisCacheManager;
import com.liu.cachespringbootstarter.dto.CacheKeyDto;
import com.liu.cachespringbootstarter.paser.Ipaser;
import org.aopalliance.intercept.MethodInvocation;

import java.util.Objects;

public class CacheHandler {
    RedisCacheManager redisCacheManager;
    Ipaser ipaser;

    public CacheHandler(RedisCacheManager redisCacheManager, Ipaser ipaser) {
        this.redisCacheManager = redisCacheManager;
        this.ipaser = ipaser;
    }

    //处理缓存
    public Object proceed(CacheAble able, MethodInvocation methodInvocation) throws Throwable {
        CacheKeyDto cacheKeyDto = ipaser.getCacheAbleKey(methodInvocation, able);
        Object dataInCache = redisCacheManager.get(cacheKeyDto.getKey());
        if (Objects.nonNull(dataInCache)) {
            return dataInCache;
        }
        // 执行代理的方法
        Object returnObj = methodInvocation.proceed();
        redisCacheManager.put(cacheKeyDto.getKey(), returnObj, able.expire(), able.timeUnit());
        return returnObj;
    }

    /**
     * 删除缓存
     */
    public void cacheDel(CacheDel cacheDel) {
        redisCacheManager.delString(cacheDel.key());
    }
}
