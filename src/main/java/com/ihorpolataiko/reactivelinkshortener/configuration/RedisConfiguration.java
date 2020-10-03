package com.ihorpolataiko.reactivelinkshortener.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

@Configuration
public class RedisConfiguration {

    private ReactiveStringRedisTemplate reactiveRedisOperations(ReactiveRedisConnectionFactory factory) {
        return new ReactiveStringRedisTemplate(factory);
    }

}
