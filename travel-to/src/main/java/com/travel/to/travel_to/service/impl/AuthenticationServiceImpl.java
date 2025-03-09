package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.UserSignInForm;
import com.travel.to.travel_to.security.JwtProvider;
import com.travel.to.travel_to.service.AuthenticationService;
import com.travel.to.travel_to.service.CustomUserDetailsService;
import com.travel.to.travel_to.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthenticationServiceImpl(
        PasswordEncoder passwordEncoder,
        UserService userService,
        CustomUserDetailsService customUserDetailsService
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    @NotNull
    public AuthUser login(
        @NotNull UserSignInForm userSignInForm
    ) {

        System.out.println(userSignInForm.getEmail()+"-------"+userSignInForm.getPassword());

        Authentication authentication = authenticate(userSignInForm.getEmail(), userSignInForm.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);

        AuthUser authUser = new AuthUser();

        authUser.setUuid(
            userService.findUserByEmail(userSignInForm.getEmail()).isPresent()
            ? userService.findUserByEmail(userSignInForm.getEmail()).get().getUuid()
            : UUID.randomUUID().toString()
        );

        authUser.setEmail(userSignInForm.getEmail());
        authUser.setPassword(passwordEncoder.encode(userSignInForm.getPassword()));
        authUser.setToken(token);

        return authUser;
    }

    @Override
    @NotNull
    public Authentication authenticate(String username, String password) {

        System.out.println(username+"---++----"+password);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        System.out.println("Sig in in user details"+ userDetails);

        if(Objects.isNull(userDetails)) {
            throw new BadCredentialsException("Invalid username and password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch"+userDetails);
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
