package com.travel.to.travel_to.form;

public class UserProfileForm {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String uuid;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
