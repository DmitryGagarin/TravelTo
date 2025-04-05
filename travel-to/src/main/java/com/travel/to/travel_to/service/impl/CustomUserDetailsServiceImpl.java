package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.repository.UserRepository;
import com.travel.to.travel_to.service.CustomUserDetailsService;
import com.travel.to.travel_to.service.UserToRoleService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserRepository userRepository;
    private final UserToRoleService userToRoleService;

    public CustomUserDetailsServiceImpl(
        UserRepository userRepository,
        UserToRoleService userToRoleService
    ) {
        this.userRepository = userRepository;
        this.userToRoleService = userToRoleService;
    }

    @Override
    public AuthUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        AuthUser authUser = new AuthUser();
        authUser
            .setEmail(user.getEmail())
            .setName(user.getName())
            .setSurname(user.getSurname())
            .setPassword(user.getPassword())
            .setUuid(user.getUuid())
            .setVerified(user.getVerified())
            .setAuthorities(userToRoleService.getAllUserRolesByUserId(user.getId()));
        return authUser;
    }
}