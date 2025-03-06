package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserSignUpForm;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.validator.UserSignUpFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
public class SignUpController {

    private UserService userService;
    private UserSignUpFormValidator userSignupFormValidator;

    @Autowired
    public void setUserService(
        UserService userService,
        UserSignUpFormValidator userSignupFormValidator
    ) {
        this.userService = userService;
        this.userSignupFormValidator = userSignupFormValidator;
    }

    @InitBinder
    private void userSignUpFormBinder(WebDataBinder binder) {
        binder.setValidator(userSignupFormValidator);
    }

    @PostMapping()
    public AuthUser signup(
        @Validated @RequestBody UserSignUpForm userSignupForm,
        BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return userService.register(userSignupForm);
    }
}
