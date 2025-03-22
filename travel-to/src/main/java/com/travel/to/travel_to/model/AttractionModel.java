package com.travel.to.travel_to.model;

import org.springframework.hateoas.PagedModel;

import java.util.List;

public class AttractionModel extends PagedModel<AttractionModel> {
    private String uuid;
    private String name;
    private String description;
    private String address;
    private List<byte[]> images;
    private String phone;
    private String website;
    private String type;
    private String openTime;
    private String closeTime;
    private Double rating;
    private String status;

    public String getUuid() {
        return uuid;
    }

    public AttractionModel setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public AttractionModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AttractionModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AttractionModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public AttractionModel setImages(List<byte[]> images) {
        this.images = images;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public AttractionModel setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public AttractionModel setWebsite(String website) {
        this.website = website;
        return this;
    }

    public String getType() {
        return type;
    }

    public AttractionModel setType(String type) {
        this.type = type;
        return this;
    }

    public String getOpenTime() {
        return openTime;
    }

    public AttractionModel setOpenTime(String openTime) {
        this.openTime = openTime;
        return this;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public AttractionModel setCloseTime(String closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public AttractionModel setRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public AttractionModel setStatus(String status) {
        this.status = status;
        return this;
    }
}
