package com.example.cacheapp.repository;

import com.example.cacheapp.entity.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, String> {
    DataEntity findByKey(String key);

    void updateValueByKey(String key, String value);
}
