package com.travel.to.travel_to.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthUser implements UserDetails {
    private Collection<? extends GrantedAuthority> authorities;
    private String jwt;
    private String message;
    private Boolean status;

    private String uuid;
    private String email;
    private String password;

    public AuthUser(
        String password,
        String email,
        Collection<? extends GrantedAuthority> authorities,
        String jwt
    ) {
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.jwt = jwt;
    }

    public AuthUser setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public String getJwt() {
        return jwt;
    }

    public AuthUser setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AuthUser setMessage(String message) {
        this.message = message;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public AuthUser setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public AuthUser setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
