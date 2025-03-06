package com.travel.to.travel_to.form;

public class UserSignUpForm {
    private String email;
    private String password;

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
