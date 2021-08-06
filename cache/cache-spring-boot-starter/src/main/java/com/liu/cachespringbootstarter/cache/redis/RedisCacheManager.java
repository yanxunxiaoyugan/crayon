package com.liu.cachespringbootstarter.cache.redis;

import com.liu.cachespringbootstarter.serial.Serial;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Component;

/**
 * 使用lettuce连接redis
 */
@Component
public class RedisCacheManager {
    private final RedisClient redisClient;
    StatefulRedisConnection<String, String> connect;
    private Serial serial;
    RedisCommands<String, String> sync;
    public RedisCacheManager(RedisProperties redisProperties, Serial serial){
        this.redisClient = RedisClient.create(RedisURI.builder().withHost(redisProperties.getHost()).withPort(redisProperties.getPort()).withPassword(redisProperties.getPassword()).build());
        connect = redisClient.connect();
        this.serial = serial;
         sync =connect.sync();
    }
    public void put(String key,Object value){

        sync.set(key,serial.serial(value));
    }
    public Object get(String key){
        return sync.get(key);
    }

    public void delString(String key){
        sync.del(key);
    }
}
