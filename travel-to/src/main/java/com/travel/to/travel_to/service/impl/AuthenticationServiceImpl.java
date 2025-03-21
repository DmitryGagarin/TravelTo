package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.UserSignInForm;
import com.travel.to.travel_to.security.jwt.JwtProvider;
import com.travel.to.travel_to.service.AuthenticationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(
        AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    @NotNull
    public AuthUser login(
        @NotNull UserSignInForm userSignInForm
    ) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userSignInForm.getEmail(),
                userSignInForm.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);

        AuthUser authUser = new AuthUser();
        authUser.setEmail(userSignInForm.getEmail());
        authUser.setToken(token);

        return authUser;
    }
}
