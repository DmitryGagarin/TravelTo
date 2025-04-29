package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.Roles;
import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.form.user.PasswordResetForm;
import com.travel.to.travel_to.form.user.UserProfileForm;
import com.travel.to.travel_to.form.user.UserRefreshPasswordForm;
import com.travel.to.travel_to.form.user.UserSignUpFirstForm;
import com.travel.to.travel_to.form.user.UserSignUpSecondForm;
import org.jetbrains.annotations.NotNull;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface UserService {

    @NotNull
    AuthUser registration(@NotNull UserSignUpFirstForm userSignupFormFirst);

    @NotNull
    AuthUser addUserInformation(@NotNull UserSignUpSecondForm userSignupFormSecond, @NotNull AuthUser authUser);

    @NotNull
    AuthUser saveChanges(@NotNull UserProfileForm userProfileForm, AuthUser authUser);

    @NotNull
    AuthUser resetPassword(@NotNull PasswordResetForm passwordResetForm);

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

    String generateEmailToken(@NotNull String email) throws NoSuchAlgorithmException;

    Boolean verificationCompleted(@NotNull String email, @NotNull String token);

    void verifyAccount(@NotNull String email);

    void resetPassword(@NotNull UserRefreshPasswordForm userRefreshPasswordForm);

    void delete(@NotNull AuthUser authUser);
}
