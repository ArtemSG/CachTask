package com.example.cacheapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StreamService {
    private final String streamKey = "cache-updates";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void sendMessage(String key, String value) {
        Map<String, String> message = new HashMap<>();
        message.put("key", key);
        message.put("value", value);
        redisTemplate.opsForStream().add(streamKey, message);
    }

    public void listenToUpdates() {
        redisTemplate.opsForStream().read(StreamOffset.latest(streamKey))
                .forEach(record -> System.out.println("Received update: " + record));
    }
}

