package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserSignInForm;
import com.travel.to.travel_to.form.UserSignUpForm;
import jakarta.validation.constraints.NotNull;

public interface UserService {

    @NotNull
    User register(@NotNull UserSignUpForm userSignupForm);

    @NotNull
    User login(@NotNull UserSignInForm userSignInForm);

    @NotNull
    User findUserByUuid(@NotNull String userUuid);
}
