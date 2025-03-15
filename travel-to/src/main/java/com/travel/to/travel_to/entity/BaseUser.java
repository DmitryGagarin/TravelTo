package com.travel.to.travel_to.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.List;

@MappedSuperclass
public class BaseUser extends UuidAbleTimedEntity implements Serializable {
    private String password;
    private String email;
    private String name;
    private String surname;
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @OneToMany(mappedBy = "owner")
    private List<Attraction> ownerAttractions;

    public String getPassword() {
        return password;
    }

    public BaseUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BaseUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public BaseUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public BaseUser setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public BaseUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserType getUserType() {
        return userType;
    }

    public BaseUser setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public List<Attraction> getOwnerAttractions() {
        return ownerAttractions;
    }

    public BaseUser setOwnerAttractions(List<Attraction> ownedAttractions) {
        this.ownerAttractions = ownedAttractions;
        return this;
    }
}
