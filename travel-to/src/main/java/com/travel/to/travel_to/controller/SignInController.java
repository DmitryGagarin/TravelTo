package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserSignInForm;
import com.travel.to.travel_to.form.UserSignUpForm;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.validator.UserSignInFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class SignInController {

    private final UserService userService;
    private final UserSignInForm userSignInForm;
    private final UserSignInFormValidator userSignInFormValidator;

    @Autowired
    public SignInController(
        UserService userService,
        UserSignInForm userSignInForm,
        UserSignInFormValidator userSignInFormValidator
    ) {
        this.userService = userService;
        this.userSignInForm = userSignInForm;
        this.userSignInFormValidator = userSignInFormValidator;
    }

    @InitBinder
    private void userSignInFormBinder(WebDataBinder binder) {
        binder.setValidator(userSignInFormValidator);
    }

    @PostMapping
    public User login(
        @RequestBody UserSignInForm userSignInForm
    ) {
        return userService.login();
    }
}
