package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.UserSignInForm;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;

@FunctionalInterface
public interface AuthenticationService {

    @NotNull
    AuthUser login(@NotNull UserSignInForm userSignInForm);

}
