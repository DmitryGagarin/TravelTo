package com.travel.to.travel_to.model;

import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Set;

public class UserModel extends PagedModel<UserModel> {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private Set<GrantedAuthority> roles;
    private String accessToken;
    private Boolean answeredUsabilityQuestionnaire;

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

    public Set<GrantedAuthority> getRole() {
        return roles;
    }

    public UserModel setRole(Set<GrantedAuthority> roles) {
        this.roles = roles;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public UserModel setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public Set<GrantedAuthority> getRoles() {
        return roles;
    }

    public UserModel setRoles(Set<GrantedAuthority> roles) {
        this.roles = roles;
        return this;
    }

    public Boolean getAnsweredUsabilityQuestionnaire() {
        return answeredUsabilityQuestionnaire;
    }

    public UserModel setAnsweredUsabilityQuestionnaire(Boolean answeredUsabilityQuestionnaire) {
        this.answeredUsabilityQuestionnaire = answeredUsabilityQuestionnaire;
        return this;
    }
}
