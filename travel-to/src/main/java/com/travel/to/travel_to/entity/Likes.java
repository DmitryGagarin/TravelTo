package com.travel.to.travel_to.entity;

import com.travel.to.travel_to.entity.common.UuidAbleTimedEntity;
import jakarta.persistence.Entity;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "likes")
public class Likes extends UuidAbleTimedEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -8968734303390108914L;
    private Long userId;
    private Long attractionId;

    public Long getUserId() {
        return userId;
    }

    public Likes setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getAttractionId() {
        return attractionId;
    }

    public Likes setAttractionId(Long attractionId) {
        this.attractionId = attractionId;
        return this;
    }
}
