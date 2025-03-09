package com.travel.to.travel_to.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseUser {
    String role = "CUSTOM_USER";

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
