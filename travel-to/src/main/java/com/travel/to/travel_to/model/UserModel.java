package com.travel.to.travel_to.model;

import java.time.LocalDateTime;

public class UserModel {
    String name;
    String surname;
    String email;
    String phone;
    LocalDateTime createdAt;

    public String getName() {
        return name;
    }

    public UserModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserModel setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserModel setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserModel setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
