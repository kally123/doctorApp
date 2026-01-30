package com.healthapp.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Reactive Redis configuration.
 */
@Configuration
@ConditionalOnProperty(name = "spring.redis.enabled", havingValue = "true", matchIfMissing = true)
public class RedisConfig {
    
    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory factory) {
        
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<Object> valueSerializer = 
                new Jackson2JsonRedisSerializer<>(Object.class);
        
        RedisSerializationContext<String, Object> context = 
                RedisSerializationContext.<String, Object>newSerializationContext(keySerializer)
                        .value(valueSerializer)
                        .hashKey(keySerializer)
                        .hashValue(valueSerializer)
                        .build();
        
        return new ReactiveRedisTemplate<>(factory, context);
    }
}
