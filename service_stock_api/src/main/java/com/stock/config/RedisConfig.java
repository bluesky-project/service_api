package com.stock.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig {
	@Value("${spring.redis.host}")
    String REDIS_HOST;

    @Value("${spring.redis.port}")
    String REDIS_PORT;

    @Value("${spring.redis.password}")
    String REDIS_PASSWORD;

    public RedissonClient redissonClient() {
        Config redisConfig = new Config();
        redisConfig.useSingleServer()
                .setAddress(REDIS_HOST + ":" + REDIS_PORT)
                //.setPassword(REDIS_PASSWORD)
                .setConnectionMinimumIdleSize(5)
                .setConnectionPoolSize(5);
        return Redisson.create(redisConfig);
    }
}