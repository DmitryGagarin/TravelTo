package com.travel.to.travel_to.form;

import com.travel.to.travel_to.entity.AttractionType;

public class AttractionCreateForm {
    private String name;
    private String description;
    private String address;
    private String image;
    private String phone;
    private String website;
    private AttractionType attractionType;
    private String openTime;
    private String closeTime;

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

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }
}
