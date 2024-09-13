package com.example.cacheapp.controller;

import com.example.cacheapp.service.CacheService;
import com.example.cacheapp.service.DataService;
import com.example.cacheapp.service.PubSubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DataController {
    private final CacheService cacheService;
    private final PubSubService pubSubService;
    private final DataService dataService;

    public DataController(CacheService cacheService, PubSubService pubSubService, DataService dataService) {
        this.cacheService = cacheService;
        this.pubSubService = pubSubService;
        this.dataService = dataService;
    }

    @GetMapping("/data/{key}")
    public ResponseEntity<String> getData(@PathVariable String key) {
        String value = cacheService.getData(key);
        return ResponseEntity.ok(value);
    }

    @PostMapping("/data/{key}")
    public ResponseEntity<Void> updateData(@PathVariable String key, @RequestBody String value) {
        cacheService.updateData(key, value);
        pubSubService.publishUpdate(key);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/invalidate/{key}")
    public String invalidateCache(@PathVariable String key) {
        dataService.invalidateCache(key);
        return "Cache invalidated for ID: " + key;
    }
}

