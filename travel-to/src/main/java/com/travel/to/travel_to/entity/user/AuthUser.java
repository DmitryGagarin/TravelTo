package com.travel.to.travel_to.entity.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AuthUser implements UserDetails {
    private String uuid;
    private String email;
    private String password;
    private String accessToken;
    private String refreshToken;
    private String name;
    private String surname;
    private String role;

    public String getUuid() {
        return uuid;
    }

    public AuthUser setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AuthUser setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public AuthUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public AuthUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public AuthUser setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }


    @Override
    public String getUsername() {
        return email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public AuthUser setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public AuthUser setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String getRole() {
        return role;
    }

    public AuthUser setRole(String role) {
        this.role = role;
        return this;
    }
}
