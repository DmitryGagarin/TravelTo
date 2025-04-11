package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.cache.ValidationTokenCacheUtil;
import com.travel.to.travel_to.email.EmailService;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.Role;
import com.travel.to.travel_to.entity.user.Roles;
import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.entity.user.UserToRole;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.form.UserRefreshPasswordForm;
import com.travel.to.travel_to.form.UserSignUpFirstForm;
import com.travel.to.travel_to.form.UserSignUpSecondForm;
import com.travel.to.travel_to.repository.UserRepository;
import com.travel.to.travel_to.repository.UserToRoleRepository;
import com.travel.to.travel_to.security.jwt.JwtProvider;
import com.travel.to.travel_to.service.RoleService;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.service.UserToRoleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserToRoleRepository userToRoleRepository;
    private final RoleService roleService;
    private final UserToRoleService userToRoleService;
    private final EmailService emailService;
    private final ValidationTokenCacheUtil validationTokenCacheUtil;

    @Autowired
    public UserServiceImpl(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        UserToRoleRepository userToRoleRepository,
        RoleService roleService,
        UserToRoleService userToRoleService,
        EmailService emailService,
        ValidationTokenCacheUtil validationTokenCacheUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userToRoleRepository = userToRoleRepository;
        this.roleService = roleService;
        this.userToRoleService = userToRoleService;
        this.emailService = emailService;
        this.validationTokenCacheUtil = validationTokenCacheUtil;
    }

    @Override
    public void delete(@NotNull AuthUser authUser) {
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

        user.setVerified(false);

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
            .setAuthorities(userToRoleService.getAllUserRolesByUserId(user.getId()))
            .setVerified(false);

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
    @Transactional
    public AuthUser resetPassword(
        @NotNull UserRefreshPasswordForm userRefreshPasswordForm
    ) {
        String encodedPassword = passwordEncoder.encode(userRefreshPasswordForm.getPassword());

        User user = findByEmail(userRefreshPasswordForm.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Can't find user by email"));

        user.setPassword(encodedPassword);
        userRepository.save(user);

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
    public void verifyAccount(@NotNull String email) {
        String token;

        try {
            token = generateVerificationToken(email);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not found");
        }

        validationTokenCacheUtil.save(token, email);

        // TODO: & sent as &amq;
        String verificationLink = "http://localhost:4000/verification-completed?email=" + email + "&token=" + token;

        String subject = "Account Verification";
        String message = "Hello,\n\nPlease verify your account by clicking the link below:\n\n" + verificationLink +
            "\n\nIf you did not request this, please ignore this email.\n\nThanks, \nYour Application Team";

        try {
            emailService.sendAccountVerificationEmail(
                email,
                verificationLink
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean verificationCompleted(
        @NotNull String email,
        @NotNull String token
    ) {
        String savedToken = validationTokenCacheUtil.findByIdentified(email);
        if (savedToken.equals(token)) {
            User user = findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Can't find user by email")
            );
            user.setVerified(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    @NotNull
    @Transactional
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
    @Transactional
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
        userRepository.save(user);

        authUser
            .setEmail(user.getEmail())
            .setName(user.getName())
            .setSurname(user.getSurname())
            .setPhone(user.getPhone());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            authUser,
            SecurityContextHolder.getContext().getAuthentication().getCredentials().toString(),
            authUser.getAuthorities()
        );

        String jwtAccessToken = JwtProvider.generateAccessToken(authentication);
        String jwtRefreshToken = JwtProvider.generateRefreshToken(authentication);

        authUser.setAccessToken(jwtAccessToken);
        authUser.setRefreshToken(jwtRefreshToken);

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

    @Override
    public String generateVerificationToken(
        @NotNull String email
    ) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        // Combine the email and the salt
        String combined = email + Base64.getEncoder().encodeToString(salt);

        // Perform SHA-256 hash on the combined value
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(combined.getBytes(StandardCharsets.UTF_8));

        // Encode the result into Base64 (to make it URL-safe)
        return Base64.getUrlEncoder().encodeToString(hashedBytes);
    }
}
