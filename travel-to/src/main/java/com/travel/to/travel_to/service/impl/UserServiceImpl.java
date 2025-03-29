package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.Role;
import com.travel.to.travel_to.entity.user.Roles;
import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.entity.user.UserToRole;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.form.UserSignUpFirstForm;
import com.travel.to.travel_to.form.UserSignUpSecondForm;
import com.travel.to.travel_to.repository.UserRepository;
import com.travel.to.travel_to.repository.UserToRoleRepository;
import com.travel.to.travel_to.security.jwt.JwtProvider;
import com.travel.to.travel_to.service.RoleService;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.service.UserToRoleService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserToRoleRepository userToRoleRepository;
    private final RoleService roleService;
    private final UserToRoleService userToRoleService;

    @Autowired
    public UserServiceImpl(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        UserToRoleRepository userToRoleRepository,
        RoleService roleService,
        UserToRoleService userToRoleService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userToRoleRepository = userToRoleRepository;
        this.roleService = roleService;
        this.userToRoleService = userToRoleService;
    }

    @Override
    public void delete(AuthUser authUser) {
        userRepository.delete(getByUuid(authUser.getUuid()));
    }

    @Override
    @NotNull
    public AuthUser registration(
        @NotNull UserSignUpFirstForm userSignupFormFirst
    ) {
        User user = new User();
        String encodedPassword = passwordEncoder.encode(userSignupFormFirst.getPassword());

        user
            .setPassword(encodedPassword)
            .setEmail(userSignupFormFirst.getEmail())
            .setCreatedAt(LocalDateTime.now())
            .setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        UserToRole userToRole = new UserToRole();
        userToRole
            .setRole(roleService.getRoleByName(Roles.USER.toString()))
            .setUser(user);
        userToRoleRepository.save(userToRole);

        AuthUser authUser = new AuthUser();
        authUser
            .setUuid(user.getUuid())
            .setEmail(user.getEmail())
            .setPassword(encodedPassword)
            .setAuthorities(userToRoleService.getAllUserRolesByUserId(user.getId()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authUser,
                encodedPassword,
                authUser.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtAccessToken = JwtProvider.generateAccessToken(authentication);
        String jwtRefreshToken = JwtProvider.generateRefreshToken(authentication);
        authUser.setAccessToken(jwtAccessToken);
        authUser.setRefreshToken(jwtRefreshToken);

        return authUser;
    }

    @Override
    @NotNull
    public AuthUser addUserInformation(
        @NotNull UserSignUpSecondForm userSignupFormSecond,
        @NotNull AuthUser authUser
    ) {
        User user = getByUuid(authUser.getUuid());

        user
            .setName(userSignupFormSecond.getName())
            .setSurname(userSignupFormSecond.getSurname());
        userRepository.save(user);

        authUser
            .setName(userSignupFormSecond.getName())
            .setSurname(userSignupFormSecond.getSurname())
            .setAuthorities(userToRoleService.getAllUserRolesByUserId(user.getId()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authUser,
                authUser.getPassword(),
                authUser.getAuthorities()
        );

        String jwtAccessToken = JwtProvider.generateAccessToken(authentication);
        String jwtRefreshToken = JwtProvider.generateRefreshToken(authentication);
        authUser.setAccessToken(jwtAccessToken);
        authUser.setRefreshToken(jwtRefreshToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authUser;
    }

    @Override
    @NotNull
    public User updateUserRole(
        @NotNull AuthUser authUser,
        @NotNull Roles newRole
    ) {
        User user = getByUuid(authUser.getUuid());
        Set<Role> currentRoles = user.getRoles();
        currentRoles.add(roleService.getRoleByName(newRole.toString()));
        user.setRoles(currentRoles);

        UserToRole userToRole = new UserToRole();
        userToRole
            .setUser(user)
            .setRole(roleService.getRoleByName(newRole.toString()));
        userToRoleRepository.save(userToRole);

        return userRepository.save(user);
    }

    @Override
    @NotNull
    public User getByUuid(
        @NotNull String userUuid
    ) {
        return userRepository.findByUuid(userUuid);
    }

    @Override
    @NotNull
    public AuthUser saveChanges(
        @NotNull UserProfileForm userProfileForm,
        @NotNull AuthUser authUser
    ) {
        User user = getByUuid(authUser.getUuid());
        user
            .setEmail(userProfileForm.getEmail())
            .setName(userProfileForm.getName())
            .setSurname(userProfileForm.getSurname())
            .setPhone(userProfileForm.getPhone())
            .setUpdatedAt(LocalDateTime.now());

        authUser
            .setEmail(user.getEmail())
            .setName(user.getName())
            .setSurname(user.getSurname())
            .setPhone(user.getPhone());

        return authUser;
    }

    @Override
    @NotNull
    public User getCurrentUser(
        @NotNull AuthUser authUser
    ) {
        return userRepository.findByUuid(authUser.getUuid());
    }

    @Override
    @NotNull
    public User save(
        @NotNull User user
    ) {
        return userRepository.save(user);
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
