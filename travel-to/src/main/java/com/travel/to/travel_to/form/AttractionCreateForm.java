package com.travel.to.travel_to.form;

import org.springframework.web.multipart.MultipartFile;

public class AttractionCreateForm {
    private String name;
    private String description;
    private String address;
    private String phone;
    private String website;
    private String attractionType;
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

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getAttractionType() {
        return attractionType;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }
}
