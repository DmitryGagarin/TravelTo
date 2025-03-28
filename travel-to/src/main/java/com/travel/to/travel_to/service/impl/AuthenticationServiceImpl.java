package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.UserRefreshTokenForm;
import com.travel.to.travel_to.form.UserSignInForm;
import com.travel.to.travel_to.security.jwt.JwtConstants;
import com.travel.to.travel_to.security.jwt.JwtProvider;
import com.travel.to.travel_to.service.AuthenticationService;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.service.UserToRoleService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserToRoleService userToRoleService;

    @Autowired
    public AuthenticationServiceImpl(
        AuthenticationManager authenticationManager,
        UserService userService,
        UserToRoleService userToRoleService
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userToRoleService = userToRoleService;
    }

    @Override
    @NotNull
    public AuthUser login(
        @NotNull UserSignInForm userSignInForm
    ) {
        Long userId = userService.findByEmail(userSignInForm.getEmail()).get().getId();

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userSignInForm.getEmail(),
                userSignInForm.getPassword(),
                userToRoleService.getAllUserRolesByUserId(userId)
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtAccessToken = JwtProvider.generateAccessToken(authentication);
        String jwtRefreshToken = JwtProvider.generateRefreshToken(authentication);


        AuthUser authUser = new AuthUser();
        authUser.setEmail(userSignInForm.getEmail());
        authUser.setAccessToken(jwtAccessToken);
        authUser.setRefreshToken(jwtRefreshToken);
        authUser.setAuthorities(userToRoleService.getAllUserRolesByUserId(userId));
        authUser.setName(
            userService.findByEmail(userSignInForm.getEmail()).isPresent()
            ? userService.findByEmail(userSignInForm.getEmail()).get().getName()
            : null
        );

        return authUser;
    }

    @Override
    @NotNull
    public AuthUser refreshToken(
        @NotNull UserRefreshTokenForm userRefreshTokenForm
    ) {
        Long userId = userService.findByEmail(userRefreshTokenForm.getEmail()).get().getId();
        String email = extractEmailFromRefreshToken(userRefreshTokenForm.getRefreshToken());
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userRefreshTokenForm.getEmail(),
                userRefreshTokenForm.getPassword(),
                userToRoleService.getAllUserRolesByUserId(userId)
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthUser authUser = new AuthUser();
        authUser.setEmail(authentication.getCredentials().toString());
        String newAccessToken = JwtProvider.generateAccessToken(authentication);
        authUser.setAccessToken(newAccessToken);
        authUser.setRefreshToken(userRefreshTokenForm.getRefreshToken());
        return authUser;
    }

    @Override
    public String extractEmailFromRefreshToken(String refreshToken) {
        SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());
        Claims claims = Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(refreshToken)
            .getPayload();
        Map<?, ?> authUserMap = (Map<?, ?>) claims.get("auth");
        return authUserMap.get("email").toString();
    }
}
