package com.travel.to.travel_to.entity.user;

import com.travel.to.travel_to.entity.common.UuidAbleEntity;
import jakarta.persistence.Entity;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "role")
public class Role extends UuidAbleEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -1595234065900421857L;

    private String role;

    public String getRole() {
        return role;
    }

    public Role setRole(String role) {
        this.role = role;
        return this;
    }
}
