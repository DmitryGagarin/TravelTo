package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.user.UserRefreshTokenForm;
import com.travel.to.travel_to.form.user.UserSignInForm;
import org.jetbrains.annotations.NotNull;

public interface AuthenticationService {

    @NotNull
    AuthUser login(@NotNull UserSignInForm userSignInForm);

    @NotNull
    AuthUser refreshToken(
        @NotNull UserRefreshTokenForm userRefreshTokenForm
    );

    @NotNull
    String extractEmailFromRefreshToken(@NotNull String refreshToken);
}
