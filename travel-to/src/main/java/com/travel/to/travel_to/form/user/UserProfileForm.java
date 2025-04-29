package com.travel.to.travel_to.form.user;

public class UserProfileForm {
    private String name;
    private String surname;
    private String email;
    private String phone;

    public String getName() {
        return name;
    }

    public UserProfileForm setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserProfileForm setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserProfileForm setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserProfileForm setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
