package com.travel.to.travel_to.entity;

import jakarta.persistence.*;

import java.util.UUID;

@MappedSuperclass
public abstract class UuidAbleEntity {

    String uuid;

    @PrePersist
    public void prePersist() {
        uuid = UUID.randomUUID().toString();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    public String getUuid() {
        return uuid;
    }

    public UuidAbleEntity setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UuidAbleEntity setId(Long id) {
        this.id = id;
        return this;
    }
}
