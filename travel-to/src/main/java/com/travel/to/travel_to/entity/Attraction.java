package com.travel.to.travel_to.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "attraction")
public class Attraction extends UuidAbleEntity{

    private String name;
    private String description;
    private String address;
    private String image;
    private String phone;
    private String website;
    @Enumerated(EnumType.STRING)
    private AttractionType type;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;

    public String getName() {
        return name;
    }

    public Attraction setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Attraction setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Attraction setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Attraction setImage(String image) {
        this.image = image;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Attraction setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public Attraction setWebsite(String website) {
        this.website = website;
        return this;
    }

    public AttractionType getType() {
        return type;
    }

    public Attraction setType(AttractionType type) {
        this.type = type;
        return this;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public Attraction setOpenTime(LocalDateTime openTime) {
        this.openTime = openTime;
        return this;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public Attraction setCloseTime(LocalDateTime closeTime) {
        this.closeTime = closeTime;
        return this;
    }
}
