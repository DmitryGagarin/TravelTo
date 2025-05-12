package com.travel.to.travel_to.entity.attraction_feature.facilities;

import com.travel.to.travel_to.entity.common.UuidAbleEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "facility")
public class Facility extends UuidAbleEntity implements Serializable{
    @Serial
    private static final long serialVersionUID = -7058419144655964702L;

    private Long attractionId;
    private String name;
    private String description;
    private byte[] image;
    private String imageFormat;
    private String openTime;
    private String closeTime;

    public Long getAttractionId() {
        return attractionId;
    }

    public Facility setAttractionId(Long attractionId) {
        this.attractionId = attractionId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Facility setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Facility setDescription(String description) {
        this.description = description;
        return this;
    }

    public byte[] getImage() {
        return image;
    }

    public Facility setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public Facility setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
        return this;
    }

    public String getOpenTime() {
        return openTime;
    }

    public Facility setOpenTime(String openTime) {
        this.openTime = openTime;
        return this;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public Facility setCloseTime(String closeTime) {
        this.closeTime = closeTime;
        return this;
    }
}
