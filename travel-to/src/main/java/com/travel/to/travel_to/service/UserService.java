package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.Roles;
import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.form.UserRefreshPasswordForm;
import com.travel.to.travel_to.form.UserSignUpFirstForm;
import com.travel.to.travel_to.form.UserSignUpSecondForm;
import jakarta.validation.constraints.NotNull;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface UserService {

    void delete(@NotNull AuthUser authUser);

    @NotNull
    AuthUser registration(@NotNull UserSignUpFirstForm userSignupFormFirst);

    @NotNull
    AuthUser resetPassword(@NotNull UserRefreshPasswordForm userRefreshPasswordForm);

    @NotNull
    AuthUser addUserInformation(@NotNull UserSignUpSecondForm userSignupFormSecond, @NotNull AuthUser authUser);

    @NotNull
    AuthUser saveChanges(@NotNull UserProfileForm userProfileForm, AuthUser authUser);

    @NotNull
    User updateUserRole(@NotNull AuthUser authUser, @NotNull Roles newRole);

    @NotNull
    User getByUuid(@NotNull String userUuid);

    @NotNull
    User getCurrentUser(@NotNull AuthUser authUser);

    @NotNull
    User save(@NotNull User user);

    Optional<User> findByEmail(@NotNull String email);

    Optional<User> findById(@NotNull Long id);

    @NotNull
    byte[] generateVerificationToken(@NotNull String email) throws NoSuchAlgorithmException;

    void verifyAccount(@NotNull String email);

}
