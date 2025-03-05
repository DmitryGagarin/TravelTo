package com.travel.to.travel_to.entity;

public class AuthUser {
    private String jwt;
    private String message;
    private Boolean status;

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

}
