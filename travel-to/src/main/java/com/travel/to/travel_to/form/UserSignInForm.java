package com.travel.to.travel_to.form;

public class UserSignInForm {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public UserSignInForm setEmail(String email) {
        this.email = email;
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
