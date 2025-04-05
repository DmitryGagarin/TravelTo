package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.configuration.CustomAuthenticationProvider;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.BaseUser;
import com.travel.to.travel_to.exception.exception.UserNotVerifiedException;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Map;
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

        Set<GrantedAuthority> authorities = userToRoleService.getAllUserRolesByUserId(userId);

        AuthUser authUser = new AuthUser();
        authUser.setEmail(userSignInForm.getEmail());
        authUser.setPassword(userSignInForm.getPassword());
        authUser.setAuthorities(authorities);

        authUser
            .setName(
                userService.findByEmail(userSignInForm.getEmail())
                    .map(BaseUser::getName)
                    .orElse(null)
            )
            .setSurname(
                userService.findByEmail(userSignInForm.getEmail())
                    .map(BaseUser::getSurname)
                    .orElse(null)
            )
            .setVerified(
                userService.findByEmail(userSignInForm.getEmail())
                    .map(BaseUser::getVerified)
                    .orElse(false)
            );

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
