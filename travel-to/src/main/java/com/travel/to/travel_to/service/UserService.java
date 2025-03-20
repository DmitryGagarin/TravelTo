package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.form.UserSignUpFirstForm;
import com.travel.to.travel_to.form.UserSignUpSecondForm;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface UserService {

    void delete(@NotNull AuthUser authUser);

    @NotNull
    AuthUser registration(@NotNull UserSignUpFirstForm userSignupFormFirst);

    @NotNull
    AuthUser addUserInformation(@NotNull UserSignUpSecondForm userSignupFormSecond, @NotNull AuthUser authUser);

    @NotNull
    User updateUserType(@NotNull AuthUser authUser);

    @NotNull
    User findByUuid(@NotNull String userUuid);

    @NotNull
    User saveChanges(@NotNull UserProfileForm userProfileForm, AuthUser authUser);

    Optional<User> findByEmail(@NotNull String email);

    Optional<User> findById(@NotNull Long id);

}
