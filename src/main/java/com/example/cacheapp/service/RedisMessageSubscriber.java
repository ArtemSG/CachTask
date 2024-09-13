package com.example.cacheapp.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RedisMessageSubscriber implements MessageListener {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("Received Redis message: " + message.toString());
        // Handle cache invalidation or other events here
    }
}
