package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserSignupForm;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.validator.UserSignupFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;
    private UserSignupFormValidator userSignupFormValidator;

    @Autowired
    public void setUserService(
        UserService userService,
        UserSignupFormValidator userSignupFormValidator
    ) {
        this.userService = userService;
        this.userSignupFormValidator = userSignupFormValidator;
    }

    @InitBinder
    private void userFormBinder(WebDataBinder binder) {
        binder.setValidator(userSignupFormValidator);
    }

    @PostMapping()
    public User signup(
        @Validated UserSignupForm userSignupForm
    ) {
        return userService.register(userSignupForm);
    }
}
