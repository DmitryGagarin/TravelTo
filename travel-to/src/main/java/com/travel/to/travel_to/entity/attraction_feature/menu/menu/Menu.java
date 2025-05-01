package com.travel.to.travel_to.entity.attraction_feature.menu.menu;

import com.travel.to.travel_to.entity.common.UuidAbleEntity;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Menu extends UuidAbleEntity {
    private Long attractionId;

    public Long getAttractionId() {
        return attractionId;
    }

    public Menu setAttractionId(Long attractionId) {
        this.attractionId = attractionId;
        return this;
    }
}
