package com.travel.to.travel_to.form;

public class UserSignInForm {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public UserSignInForm setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserSignInForm setPassword(String password) {
        this.password = password;
        return this;
    }
}
