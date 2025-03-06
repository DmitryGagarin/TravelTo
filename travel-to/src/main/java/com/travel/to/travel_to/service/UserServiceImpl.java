package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.form.UserSignUpForm;
import com.travel.to.travel_to.repository.UserRepository;
import com.travel.to.travel_to.security.JwtProvider;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    @NotNull
    public AuthUser register(
        @NotNull UserSignUpForm userSignupForm
    ) {
        User user = new User();
        user
            .setPassword(new BCryptPasswordEncoder().encode(userSignupForm.getPassword()))
            .setEmail(userSignupForm.getEmail())
            .setCreatedAt(LocalDateTime.now())
            .setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userSignupForm.getEmail(), userSignupForm.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);

        AuthUser authUser = new AuthUser(userSignupForm.getEmail(), userSignupForm.getPassword(), List.of(), token);
        authUser.setJwt(token);
        authUser.setMessage("Register Success");
        authUser.setStatus(true);

        return authUser;
    }

    @Override
    public User findUserByUuid(
        @NotNull String userUuid
    ) {
        return userRepository.findUserByUuid(userUuid);
    }

    @Override
    @NotNull
    @Transactional
    public User saveChanges(
        @NotNull UserProfileForm userProfileForm,
        @NotNull AuthUser authUser
    ) {
        User user = findUserByUuid(authUser.getUuid());
        user
            .setEmail(userProfileForm.getEmail())
            .setName(userProfileForm.getName())
            .setSurname(userProfileForm.getSurname())
            .setPhone(userProfileForm.getPhone())
            .setUpdatedAt(LocalDateTime.now());
        return user;
    }

    @Override
    public UserDetails loadUserByEmail(String email) {
        Optional<User> user = findUserByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                authorities
        );
    }

    @Override
    public Optional<User> findUserByEmail(
        @NotNull String email
    ) {
        return userRepository.findUserByEmail(email);
    }
}
