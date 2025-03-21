package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.entity.user.UserType;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.form.UserSignUpFirstForm;
import com.travel.to.travel_to.form.UserSignUpSecondForm;
import com.travel.to.travel_to.repository.UserRepository;
import com.travel.to.travel_to.security.jwt.JwtProvider;
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
    public void delete(AuthUser authUser) {
        userRepository.delete(findByUuid(authUser.getUuid()));
    }

    @Override
    @NotNull
    public AuthUser registration(@NotNull UserSignUpFirstForm userSignupFormFirst) {
        User user = new User();
        String encodedPassword = passwordEncoder.encode(userSignupFormFirst.getPassword());

        user
            .setUserType(UserType.VISITOR)
            .setPassword(encodedPassword)
            .setEmail(userSignupFormFirst.getEmail())
            .setCreatedAt(LocalDateTime.now())
            .setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        User savedUser = userRepository.findByEmail(userSignupFormFirst.getEmail()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        AuthUser authUser = new AuthUser();
        authUser.setUuid(savedUser.getUuid());
        authUser.setEmail(savedUser.getEmail());
        authUser.setPassword(encodedPassword);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authUser,
                encodedPassword,
                authUser.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = JwtProvider.generateToken(authentication);
        authUser.setToken(jwtToken);

        return authUser;
    }

    @Override
    @NotNull
    public AuthUser addUserInformation(
        @NotNull UserSignUpSecondForm userSignupFormSecond,
        @NotNull AuthUser authUser
    ) {
        User user = findByUuid(authUser.getUuid());

        user
            .setName(userSignupFormSecond.getName())
            .setSurname(userSignupFormSecond.getSurname());
        userRepository.save(user);

        authUser.setName(userSignupFormSecond.getName());
        authUser.setSurname(userSignupFormSecond.getSurname());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authUser,
                authUser.getPassword(),
                authUser.getAuthorities()
        );

        String jwtToken = JwtProvider.generateToken(authentication);
        authUser.setToken(jwtToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authUser;
    }

    @Override
    @NotNull
    public User updateUserType(AuthUser authUser, UserType userType) {
        User user = findByUuid(authUser.getUuid());
        user.setUserType(userType);
        return userRepository.save(user);
    }

    @Override
    @NotNull
    public User findByUuid(
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
        User user = findByUuid(authUser.getUuid());
        user
            .setEmail(userProfileForm.getEmail())
            .setName(userProfileForm.getName())
            .setSurname(userProfileForm.getSurname())
            .setPhone(userProfileForm.getPhone())
            .setUpdatedAt(LocalDateTime.now());
        return user;
    }

    @Override
    public Optional<User> findByEmail(
        @NotNull String email
    ) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(
        @NotNull Long id
    ) {
        return userRepository.findById(id);
    }
}
