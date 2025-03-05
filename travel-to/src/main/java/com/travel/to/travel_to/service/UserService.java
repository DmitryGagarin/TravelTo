package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserSignUpForm;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {

    @NotNull
    User register(@NotNull UserSignUpForm userSignupForm);

    @NotNull
    User findUserByUuid(@NotNull String userUuid);

    @NotNull
    UserDetails loadUserByUsername(@NotNull String username);

    Optional<User> findUserByEmail(@NotNull String email);

    Optional<User> findUserByUsername(@NotNull String username);


}
