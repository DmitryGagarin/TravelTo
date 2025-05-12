package com.travel.to.travel_to.model;

import org.springframework.hateoas.PagedModel;

public class FacilityModel extends PagedModel<FacilityModel> {
    private String name;
    private String description;
    private byte[] image;
    private String imageFormat;
    private String openTime;
    private String closeTime;

    public String getName() {
        return name;
    }

    public FacilityModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FacilityModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public byte[] getImage() {
        return image;
    }

    public FacilityModel setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public FacilityModel setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
        return this;
    }

    public String getOpenTime() {
        return openTime;
    }

    public FacilityModel setOpenTime(String openTime) {
        this.openTime = openTime;
        return this;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public FacilityModel setCloseTime(String closeTime) {
        this.closeTime = closeTime;
        return this;
    }
}
