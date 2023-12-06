package com.msb.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-06 09:27
 */
@Configuration
@EnableCaching
public class MyRedisConfig extends CachingConfigurerSupport {

    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {

        RedisSerializer serializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();

        RedisSerializationContext<String, Object> context = RedisSerializationContext.newSerializationContext()
                .key(serializer)
                .value(valueSerializer)
                .hashKey(serializer)
                .hashValue(valueSerializer).build();

        ReactiveRedisTemplate<String, Object> reactiveRedisTemplate = new ReactiveRedisTemplate<String, Object>(factory, context);
        return reactiveRedisTemplate;
    }

}
