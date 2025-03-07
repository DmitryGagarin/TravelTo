package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.UserSignInForm;
import com.travel.to.travel_to.security.JwtProvider;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public AuthenticationServiceImpl(
        PasswordEncoder passwordEncoder,
        UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    @NotNull
    public AuthUser login(
        @NotNull UserSignInForm userSignInForm
    ) {
        Authentication authentication = authenticate(userSignInForm.getEmail(), userSignInForm.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);
        AuthUser authUser = new AuthUser(userSignInForm.getPassword(), userSignInForm.getEmail(), List.of(), token);

        authUser.setMessage("Login success");
        authUser.setJwt(token);
        authUser.setStatus(true);
        authUser.setUuid(userService.findUserByEmail(userSignInForm.getEmail()).get().getUuid());

        return authUser;
    }

    @Override
    @NotNull
    public Authentication authenticate(String email, String password) {
        UserDetails userDetails = userService.loadUserByEmail(email);
        if(Objects.isNull(userDetails)) {
            throw new BadCredentialsException("Invalid email and password");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
    }
}
