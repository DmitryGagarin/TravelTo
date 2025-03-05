package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserSignInForm;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.validator.UserSignInFormValidator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/signin")
public class SignInController {

    private final UserService userService;
    private final UserSignInFormValidator userSignInFormValidator;

    @Autowired
    public SignInController(
        UserService userService,
        UserSignInFormValidator userSignInFormValidator
    ) {
        this.userService = userService;
        this.userSignInFormValidator = userSignInFormValidator;
    }

    @InitBinder
    private void userSignInFormBinder(WebDataBinder binder) {
        binder.setValidator(userSignInFormValidator);
    }

    @PostMapping
    public User signIn(
        @Validated UserSignInForm userSignInForm,
        BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return userService.login(userSignInForm);
    }
}
