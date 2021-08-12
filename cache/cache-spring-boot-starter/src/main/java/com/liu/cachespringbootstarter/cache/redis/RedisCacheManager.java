package com.liu.cachespringbootstarter.cache.redis;

import io.lettuce.core.KeyValue;
import io.lettuce.core.SetArgs;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Component;

import com.liu.cachespringbootstarter.serial.Serial;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 使用lettuce连接redis
 */
@Component
public class RedisCacheManager<T> {
	private final RedisClient redisClient;
	StatefulRedisConnection<String, String> connect;
	private final Serial serial;
	RedisCommands<String, String> sync;

	public RedisCacheManager(RedisProperties redisProperties, Serial serial) {
		this.redisClient = RedisClient
				.create(RedisURI.builder().withHost(redisProperties.getHost())
						.withPort(redisProperties.getPort())
						.withPassword(redisProperties.getPassword()).build());
		connect = redisClient.connect();
		this.serial = serial;
		sync = connect.sync();
	}

	public void put(String key, Object value) {

		sync.set(key, serial.serial(value));
	}

	public void put(String key, Object value, int expire, TimeUnit timeUnit) {
		SetArgs setArgs = new SetArgs();
		setArgs.ex( timeUnit.toSeconds(expire));
		sync.set(key, serial.serial(value),setArgs);
	}

	public Object get(String key) {
		return sync.get(key);
	}


	public List<KeyValue<String, String>> mget(String... key){
		List<KeyValue<String, String>> mget = sync.mget(key);
		return mget;
	}

	public void delString(String key) {
		sync.del(key);
	}
}
