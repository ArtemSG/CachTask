package com.example.cacheapp.service;

import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PubSubService {
    private final RedisTemplate<String, String> redisTemplate;

    public PubSubService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publishUpdate(String key) {
        redisTemplate.convertAndSend("cache-invalidation", key);
    }

    @EventListener
    public void handleMessage(String key) {
        redisTemplate.delete(key);
    }
}
