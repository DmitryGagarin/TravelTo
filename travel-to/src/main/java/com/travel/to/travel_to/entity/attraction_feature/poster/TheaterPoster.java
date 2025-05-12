package com.travel.to.travel_to.entity.attraction_feature.poster;

import com.travel.to.travel_to.entity.common.UuidAbleEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "theater_poster")
public class TheaterPoster extends UuidAbleEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -2494290782905924595L;

    private byte[] image;
    private String imageFormat;
    private Long attractionId;

    public byte[] getImage() {
        return image;
    }

    public TheaterPoster setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public TheaterPoster setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
        return this;
    }

    public Long getAttractionId() {
        return attractionId;
    }

    public TheaterPoster setAttractionId(Long attractionId) {
        this.attractionId = attractionId;
        return this;

    }
}
