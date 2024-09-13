package com.example.cacheapp.service;

import com.example.cacheapp.entity.DataEntity;
import com.example.cacheapp.repository.DataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CacheService {
    private final RedisTemplate<String, String> redisTemplate;
    private final DataRepository dataRepository;

    @Value("${cache.expiration}")
    private Long cacheExpiration;

    public CacheService(RedisTemplate<String, String> redisTemplate, DataRepository dataRepository) {
        this.redisTemplate = redisTemplate;
        this.dataRepository = dataRepository;
    }

    public String getData(String key) {
        String cacheValue = redisTemplate.opsForValue().get(key);
        if (cacheValue == null) {
            DataEntity dataEntity = dataRepository.findByKey(key);
            if (dataEntity != null) {
                redisTemplate.opsForValue().set(key, dataEntity.getValue(), Duration.ofSeconds(cacheExpiration));
                return dataEntity.getValue();
            } else {
                return "Data not found";
            }
        }
        return cacheValue;
    }

    public void updateData(String key, String value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(cacheExpiration));
        dataRepository.updateValueByKey(key, value);
    }
}
