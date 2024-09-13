package com.example.cacheapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class DataEntity {
    @Id
    private String id;
    private String data;
    private String value;
}