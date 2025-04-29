package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.configuration.CustomAuthenticationProvider;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.exception.exception.UserNotVerifiedException;
import com.travel.to.travel_to.form.user.UserRefreshTokenForm;
import com.travel.to.travel_to.form.user.UserSignInForm;
import com.travel.to.travel_to.security.jwt.JwtConstants;
import com.travel.to.travel_to.security.jwt.JwtProvider;
import com.travel.to.travel_to.service.AuthenticationService;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.service.UserToRoleService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final UserService userService;
    private final UserToRoleService userToRoleService;

    @Autowired
    public AuthenticationServiceImpl(
        CustomAuthenticationProvider customAuthenticationProvider,
        UserService userService,
        UserToRoleService userToRoleService
    ) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.userService = userService;
        this.userToRoleService = userToRoleService;
    }

    @Override
    @NotNull
    public AuthUser login(
        @NotNull UserSignInForm userSignInForm
    ) {
        Long userId = userService.findByEmail(userSignInForm.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"))
            .getId();

        User user = userService.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<GrantedAuthority> authorities = userToRoleService.getAllUserRolesByUserId(userId);

        AuthUser authUser = new AuthUser();
        authUser
            .setEmail(userSignInForm.getEmail())
            .setPassword(userSignInForm.getPassword())
            .setAuthorities(authorities)
            .setPhone(user.getPhone())
            .setName(user.getName())
            .setSurname(user.getSurname())
            .setVerified(user.getVerified());

        // TODO: correct redirect in case of not verified
        if (!authUser.getVerified() || authUser.getVerified() == null) {
            throw new UserNotVerifiedException("not verified");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            authUser,
            userSignInForm.getPassword(),
            authUser.getAuthorities()
        );

        authentication = customAuthenticationProvider.authenticate(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtAccessToken = JwtProvider.generateAccessToken(authentication);
        String jwtRefreshToken = JwtProvider.generateRefreshToken(authentication);

        authUser.setAccessToken(jwtAccessToken);
        authUser.setRefreshToken(jwtRefreshToken);

        return authUser;
    }

    @Override
    @NotNull
    public AuthUser refreshToken(
        @NotNull UserRefreshTokenForm userRefreshTokenForm
    ) {
        Long userId = null;
        if (userService.findByEmail(userRefreshTokenForm.getEmail()).isPresent()) {
            userId = userService.findByEmail(userRefreshTokenForm.getEmail()).get().getId();
        }
        String email = extractEmailFromRefreshToken(userRefreshTokenForm.getRefreshToken());
        Authentication authentication = customAuthenticationProvider.authenticate(
            new UsernamePasswordAuthenticationToken(
                userRefreshTokenForm.getEmail(),
                userRefreshTokenForm.getPassword(),
                userToRoleService.getAllUserRolesByUserId(Objects.requireNonNull(userId))
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
    @NotNull
    public String extractEmailFromRefreshToken(@NotNull String refreshToken) {
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
