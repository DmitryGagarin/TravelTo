package com.travel.to.travel_to.form;

public class UserSignUpForm {
    private String username;
    private String email;
    private String password;

    public String getUsername() {
        return username;
    }

    public UserSignUpForm setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserSignUpForm setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserSignUpForm setPassword(String password) {
        this.password = password;
        return this;
    }
}
