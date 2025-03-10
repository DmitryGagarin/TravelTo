package com.travel.to.travel_to.form;

import com.travel.to.travel_to.entity.AttractionType;

import java.time.LocalDateTime;

public class AttractionCreateForm {
    private String name;
    private String description;
    private String address;
    private String image;
    private String phone;
    private String website;
    private AttractionType attractionType;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public AttractionType getAttractionType() {
        return attractionType;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }
}
