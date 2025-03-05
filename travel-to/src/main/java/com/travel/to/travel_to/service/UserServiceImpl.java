package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserSignInForm;
import com.travel.to.travel_to.form.UserSignUpForm;
import com.travel.to.travel_to.repository.UserRepository;
import com.travel.to.travel_to.security.JwtProvider;
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
import java.util.Date;
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
    public User register(
        @NotNull UserSignUpForm userSignupForm
    ) {
        User user = new User();
        user.setUsername(userSignupForm.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userSignupForm.getPassword()));
        user.setEmail(userSignupForm.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userSignupForm.getEmail(), userSignupForm.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);

        AuthUser authUser = new AuthUser();
        authUser.setJwt(token);
        authUser.setMessage("Register Success");
        authUser.setStatus(true);

        return userRepository.save(user);
    }

    @Override
    @NotNull
    public User findUserByUuid(
        @NotNull String userUuid
    ) {
        return userRepository.findUserByUuid(userUuid);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = findUserByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                authorities);
    }

    @Override
    public Optional<User> findUserByEmail(
        @NotNull String email
    ) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> findUserByUsername(
        @NotNull String username
    ) {
        return userRepository.findUserByUsername(username);
    }

}
