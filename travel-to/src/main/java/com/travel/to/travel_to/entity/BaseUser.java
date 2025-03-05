package com.travel.to.travel_to.entity;

import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public abstract class BaseUser extends UuidAbleTimedEntity implements Serializable {

    String username;
    String password;
    String email;

    public String getUsername() {
        return username;
    }

    public BaseUser setUsername(String username) {
        this.username = username;
        return this;
    }

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
}
