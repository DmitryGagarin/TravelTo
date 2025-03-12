package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.UserSignInForm;
import com.travel.to.travel_to.service.AuthenticationService;
import com.travel.to.travel_to.validator.UserSignInFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/signin")
public class SignInController {

    private final UserSignInFormValidator userSignInFormValidator;
    private final AuthenticationService authenticationService;

    @Autowired
    public SignInController(
        UserSignInFormValidator userSignInFormValidator,
        AuthenticationService authenticationService
    ) {
        this.userSignInFormValidator = userSignInFormValidator;
        this.authenticationService = authenticationService;
    }

    @InitBinder
    private void userSignInFormBinder(WebDataBinder binder) {
        binder.setValidator(userSignInFormValidator);
    }

    @PostMapping()
    public AuthUser signIn(
        @Validated @RequestBody UserSignInForm userSignInForm,
        BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return authenticationService.login(userSignInForm);
    }
}
