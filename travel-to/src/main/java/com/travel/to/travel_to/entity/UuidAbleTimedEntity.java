package com.travel.to.travel_to.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public class UuidAbleTimedEntity extends UuidAbleEntity {

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime updatedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UuidAbleTimedEntity setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UuidAbleTimedEntity setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
