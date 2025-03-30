package com.travel.to.travel_to.entity.attraction;

import com.travel.to.travel_to.entity.user.UuidAbleEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "attraction_image")
public class AttractionImage extends UuidAbleEntity {
    private Long attractionId;
    private byte[] image;
    private String imageFormat;

    public Long getAttractionId() {
        return attractionId;
    }

    public AttractionImage setAttractionId(Long attractionId) {
        this.attractionId = attractionId;
        return this;
    }

    public byte[] getImage() {
        return image;
    }

    public AttractionImage setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public AttractionImage setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
        return this;
    }
}
