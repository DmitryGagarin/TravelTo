package com.travel.to.travel_to.entity.attraction;

import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.entity.user.UuidAbleEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "attraction")
public class Attraction extends UuidAbleEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2849542955548186627L;

    private String ownerTelegram;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String website;
    private String type;
    private String openTime;
    private String closeTime;
    private Double rating;
    private String status;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "liked_by")
    private User likedBy;

    public String getOwnerTelegram() {
        return ownerTelegram;
    }

    public Attraction setOwnerTelegram(String ownerTelegram) {
        this.ownerTelegram = ownerTelegram;
        return this;
    }

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

    public String getType() {
        return type;
    }

    public Attraction setType(String type) {
        this.type = type;
        return this;
    }

    public String getOpenTime() {
        return openTime;
    }

    public Attraction setOpenTime(String openTime) {
        this.openTime = openTime;
        return this;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public Attraction setCloseTime(String closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public Attraction setRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Attraction setOwner(User ownerUser) {
        this.owner = ownerUser;
        return this;
    }

    public User getLikedBy() {
        return likedBy;
    }

    public Attraction setLikedBy(User likedBy) {
        this.likedBy = likedBy;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Attraction setStatus(String status) {
        this.status = status;
        return this;
    }
}
