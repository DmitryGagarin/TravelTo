package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.PasswordResetForm;
import com.travel.to.travel_to.form.UserRefreshPasswordForm;
import com.travel.to.travel_to.form.UserRefreshTokenForm;
import com.travel.to.travel_to.form.UserSignInForm;
import com.travel.to.travel_to.service.AuthenticationService;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.validator.user.UserSignInFormValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "Signin/login controller",
    description = "Controller is responsible for login actions and utilities such as account verification, returns AuthUser"
)
@RestController
@RequestMapping("/signin")
public class SignInController {

    private final UserSignInFormValidator userSignInFormValidator;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Autowired
    public SignInController(
        UserSignInFormValidator userSignInFormValidator,
        AuthenticationService authenticationService,
        UserService userService
    ) {
        this.userSignInFormValidator = userSignInFormValidator;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @InitBinder("userSignInForm")
    private void userSignInFormBinder(WebDataBinder binder) {
        binder.setValidator(userSignInFormValidator);
    }

    @GetMapping("/verification-completed")
    public Boolean verificationCompleted(
        @RequestParam String email,
        @RequestParam String token
    ) {
        return userService.verificationCompleted(email, token);
    }

    // TODO: needed to be rewrote to form probably
    @PostMapping("/verify-account/{email}")
    public void verifyAccount(
        @PathVariable String email
    ) {
        userService.verifyAccount(email);
    }

    @PostMapping("/reset-password")
    public void resetUserPassword(
        @RequestBody UserRefreshPasswordForm userRefreshPasswordForm
    ) {
        userService.resetPassword(userRefreshPasswordForm);
    }

    @PostMapping("/reset-password-completion")
    public AuthUser resetUserPasswordCompletion(
        @RequestBody PasswordResetForm passwordResetForm
    ) {
        return userService.resetPassword(passwordResetForm);
    }

    @PostMapping("")
    public AuthUser signIn(
        @Validated @RequestBody UserSignInForm userSignInForm,
        BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return authenticationService.login(userSignInForm);
    }

    @PostMapping("/refresh")
    public AuthUser refresh(
        @RequestBody UserRefreshTokenForm userRefreshTokenForm,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        return authenticationService.refreshToken(userRefreshTokenForm);
    }
}
