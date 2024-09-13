package com.example.cacheapp.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

@Service
public class DataService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Simulate a database or external service fetch
    @Cacheable(value = "dataCache", key = "#id")
    public String fetchDataFromBackend(String id) {
        System.out.println("Fetching from database...");
        return "Data for ID: " + id;
    }

    // Method to invalidate cache manually
    public void invalidateCache(String id) {
        redisTemplate.delete(id);
    }
}
