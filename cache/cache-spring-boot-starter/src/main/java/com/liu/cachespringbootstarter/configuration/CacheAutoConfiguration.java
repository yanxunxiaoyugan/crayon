package com.liu.cachespringbootstarter.configuration;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.annotation.CacheDel;
import com.liu.cachespringbootstarter.cache.redis.RedisCacheManager;
import com.liu.cachespringbootstarter.configuration.pointcut.MyPointcutAdvisor;
import com.liu.cachespringbootstarter.handler.CacheHandler;
import com.liu.cachespringbootstarter.interceptor.CacheAbleMethodInterceptor;
import com.liu.cachespringbootstarter.interceptor.CacheDelMethodInterceptor;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 默认启用
 */
@Configuration
@ConditionalOnBean(MyCacheProperties.class)
@ConditionalOnProperty(value = "mycache.enable",havingValue = "true",matchIfMissing = true)
public class CacheAutoConfiguration {
    @Autowired
    private MyCacheProperties cacheProperties;
    @Bean
    public CacheAbleMethodInterceptor cacheAbleMethodInterceptor(CacheHandler cacheHandler) {
        return new CacheAbleMethodInterceptor(cacheHandler);
    }

    @Bean
    public CacheDelMethodInterceptor cacheDelMethodInterceptor(CacheHandler cacheHandler){
        return new CacheDelMethodInterceptor(cacheHandler);
    }

    @Bean
    public CacheHandler cacheHandler(RedisCacheManager redisCacheManager){
        return new CacheHandler(redisCacheManager);
    }

    @Bean
    public AbstractPointcutAdvisor cacheAbleAdvisor(CacheAbleMethodInterceptor cacheAbleMethodInterceptor){

        AbstractPointcutAdvisor cacheAbleAdvisor = new MyPointcutAdvisor(CacheAble.class,cacheAbleMethodInterceptor);
        cacheAbleAdvisor.setOrder(cacheProperties.getOrder());
        return cacheAbleAdvisor;
    }

    @Bean
    public  AbstractPointcutAdvisor cacheDelAdvisor(CacheDelMethodInterceptor cacheDelMethodInterceptor){
        AbstractPointcutAdvisor cacheDelAdvisor = new MyPointcutAdvisor(CacheDel.class,cacheDelMethodInterceptor);
        cacheDelAdvisor.setOrder(cacheProperties.getOrder());
        return cacheDelAdvisor;
    }
}
