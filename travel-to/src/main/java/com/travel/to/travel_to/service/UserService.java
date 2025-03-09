package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.form.UserSignUpForm;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface UserService {

    @NotNull
    AuthUser register(@NotNull UserSignUpForm userSignupForm);

    @NotNull
    User findUserByUuid(@NotNull String userUuid);

    @NotNull
    User saveChanges(@NotNull UserProfileForm userProfileForm, AuthUser authUser);

    Optional<User> findUserByEmail(@NotNull String email);

}
