package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.UserSignInForm;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    @NotNull
    Authentication authenticate(@NotNull String username, @NotNull String password);

    @NotNull
    AuthUser login(@NotNull UserSignInForm userSignInForm);

}
