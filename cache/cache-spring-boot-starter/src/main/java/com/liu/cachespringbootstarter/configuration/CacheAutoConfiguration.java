package com.liu.cachespringbootstarter.configuration;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.annotation.CacheDel;
import com.liu.cachespringbootstarter.beanPostprocessor.WarmUpBeanPostProcessor;
import com.liu.cachespringbootstarter.cache.redis.RedisCacheManager;
import com.liu.cachespringbootstarter.configuration.pointcut.MyPointcutAdvisor;
import com.liu.cachespringbootstarter.handler.CacheHandler;
import com.liu.cachespringbootstarter.interceptor.CacheAbleMethodInterceptor;
import com.liu.cachespringbootstarter.interceptor.CacheDelMethodInterceptor;
import com.liu.cachespringbootstarter.paser.Ipaser;
import com.liu.cachespringbootstarter.paser.SpringElPaser;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 默认启用
 */
@Configuration
@ConditionalOnBean({MyCacheProperties.class})
@DependsOn({"myCacheProperties"})
@ConditionalOnProperty(value = "mycache.enable", havingValue = "true", matchIfMissing = true)
public class CacheAutoConfiguration {
    @Autowired
    private MyCacheProperties cacheProperties;

    @Value("${spring.redis.host}")
    public String redisHost;
    @Value("${spring.redis.port}")
    public String redisPort;
    @Value("${spring.redis.password}")
    public String redisPassword;

    @Bean
    @ConditionalOnMissingBean(Ipaser.class)
    public Ipaser autoloadCacheScriptParser() {
        return new SpringElPaser();
    }

    @Bean
    public WarmUpBeanPostProcessor warmUpBeanPostProcessor(){
        return new WarmUpBeanPostProcessor();
    }
    @Bean
    public Redisson redisson(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + redisHost + ":" +redisPort);
        config.useSingleServer().setPassword(redisPassword);
        Redisson redissonClient = (Redisson) Redisson.create(config);
        return redissonClient;
    }
    @Bean
    public CacheAbleMethodInterceptor cacheAbleMethodInterceptor(CacheHandler cacheHandler) {
        return new CacheAbleMethodInterceptor(cacheHandler);
    }

    @Bean
    public CacheDelMethodInterceptor cacheDelMethodInterceptor(CacheHandler cacheHandler) {
        return new CacheDelMethodInterceptor(cacheHandler);
    }

    @Bean
    public CacheHandler cacheHandler(RedisCacheManager redisCacheManager, Ipaser ipaser) {
        return new CacheHandler(redisCacheManager, ipaser);
    }

    @Bean
    public AbstractPointcutAdvisor cacheAbleAdvisor(CacheAbleMethodInterceptor cacheAbleMethodInterceptor) {

        AbstractPointcutAdvisor cacheAbleAdvisor = new MyPointcutAdvisor(CacheAble.class, cacheAbleMethodInterceptor);
        cacheAbleAdvisor.setOrder(cacheProperties.getOrder());
        return cacheAbleAdvisor;
    }

    @Bean
    public AbstractPointcutAdvisor cacheDelAdvisor(CacheDelMethodInterceptor cacheDelMethodInterceptor) {
        AbstractPointcutAdvisor cacheDelAdvisor = new MyPointcutAdvisor(CacheDel.class, cacheDelMethodInterceptor);
        cacheDelAdvisor.setOrder(cacheProperties.getOrder());
        return cacheDelAdvisor;
    }

    public MyCacheProperties getCacheProperties() {
        return cacheProperties;
    }

    public void setCacheProperties(MyCacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }
}
