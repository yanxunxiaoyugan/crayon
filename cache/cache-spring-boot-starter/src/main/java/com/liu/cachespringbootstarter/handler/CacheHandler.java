package com.liu.cachespringbootstarter.handler;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.annotation.CacheDel;
import com.liu.cachespringbootstarter.cache.redis.RedisCacheManager;
import com.liu.cachespringbootstarter.constants.RedisKeyDef;
import com.liu.cachespringbootstarter.dto.CacheKeyDto;
import com.liu.cachespringbootstarter.paser.Ipaser;
import com.liu.cachespringbootstarter.utils.CacheUtls;
import org.aopalliance.intercept.MethodInvocation;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CacheHandler {
    RedisCacheManager redisCacheManager;
    Ipaser ipaser;
    @Autowired
    Redisson redisson;
    @Autowired
    private MagicHandler magicHandler;

    public CacheHandler(RedisCacheManager redisCacheManager, Ipaser ipaser) {
        this.redisCacheManager = redisCacheManager;
        this.ipaser = ipaser;
    }

    //处理缓存
    public Object proceed(CacheAble able, MethodInvocation methodInvocation) throws Throwable {


        //先判断是不是magic模式
        Method method = methodInvocation.getMethod();
        if(CacheUtls.isMagic(able,method)){
            return magicHandler.handler(able,methodInvocation);

        }
        CacheKeyDto cacheKeyDto = ipaser.getCacheAbleKey(methodInvocation, able);
        Object dataInCache = redisCacheManager.get(cacheKeyDto.getKey());
        if (Objects.nonNull(dataInCache)) {
            //判断是否需要自动加载
            if(able.autoLoad() ){

            }
            return dataInCache;
        }
        //加锁，防止缓存穿透
        RLock redissonLock = redisson.getLock(cacheKeyDto.getKey() + RedisKeyDef.CACHE_LOCK_SUFFIX);
        //尝试获取锁
        boolean  success= redissonLock.tryLock(1, TimeUnit.SECONDS);
        if(success){
            //休眠2秒钟，再去获取缓存数据
            TimeUnit.SECONDS.sleep(2);
            Object currentData = redisCacheManager.get(cacheKeyDto.getKey());
            if(currentData == null) {
                //如果缓存中依旧没有数据，则执行代理的方法
                Object returnObj = methodInvocation.proceed();
                redisCacheManager.put(cacheKeyDto.getKey(), returnObj, able.expire(), able.timeUnit());
                return returnObj;
            }
            redissonLock.unlock();
            return currentData;
        }else{
            //获取不到锁直接返回
            return "请刷新页面重试";
        }

    }

    /**
     * 删除缓存
     */
    public void cacheDel(CacheDel cacheDel) {
        redisCacheManager.delString(cacheDel.key());
    }
}
