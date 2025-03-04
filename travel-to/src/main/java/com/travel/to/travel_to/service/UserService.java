package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserSignupForm;
import jakarta.validation.constraints.NotNull;

public interface UserService {

    @NotNull
    User register(UserSignupForm userSignupForm);
}
