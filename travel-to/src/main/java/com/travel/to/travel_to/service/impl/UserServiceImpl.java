package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.entity.UserType;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.form.UserSignUpForm;
import com.travel.to.travel_to.repository.UserRepository;
import com.travel.to.travel_to.security.JwtProvider;
import com.travel.to.travel_to.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @NotNull
    public AuthUser register(
        @NotNull UserSignUpForm userSignupForm
    ) {
        User user = new User();
        String encodedPassword = passwordEncoder.encode(userSignupForm.getPassword());

        user
            .setUserType(UserType.VISITOR)
            .setPassword(encodedPassword)
            .setEmail(userSignupForm.getEmail())
            .setCreatedAt(LocalDateTime.now())
            .setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userSignupForm.getEmail(),
                encodedPassword
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User savedUser = userRepository.findByEmail(userSignupForm.getEmail()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        String jwtToken = JwtProvider.generateToken(authentication);

        AuthUser authUser = new AuthUser();
        authUser.setUuid(savedUser.getUuid());
        authUser.setEmail(savedUser.getEmail());
        authUser.setPassword(encodedPassword);
        authUser.setUuid(savedUser.getUuid());
        authUser.setToken(jwtToken);

        return authUser;
    }

    @Override
    public User findUserByUuid(
        @NotNull String userUuid
    ) {
        return userRepository.findByUuid(userUuid);
    }

    @Override
    @NotNull
    @Transactional
    public User saveChanges(
        @NotNull UserProfileForm userProfileForm,
        @NotNull AuthUser authUser
    ) {
        User user = findUserByUuid(userProfileForm.getUuid());
        user
            .setEmail(userProfileForm.getEmail())
            .setName(userProfileForm.getName())
            .setSurname(userProfileForm.getSurname())
            .setPhone(userProfileForm.getPhone())
            .setUpdatedAt(LocalDateTime.now());
        return user;
    }

    @Override
    public Optional<User> findUserByEmail(
        @NotNull String email
    ) {
        return userRepository.findByEmail(email);
    }
}
