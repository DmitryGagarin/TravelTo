package com.travel.to.travel_to.entity.user;

import jakarta.persistence.Entity;

@Entity(name = "role")
public class Role extends UuidAbleEntity {
    private String role;

    public String getRole() {
        return role;
    }

    public Role setRole(String role) {
        this.role = role;
        return this;
    }
}
