package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.UserSignUpFirstForm;
import com.travel.to.travel_to.form.UserSignUpSecondForm;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.validator.user.UserSignUpFirstFormValidator;
import com.travel.to.travel_to.validator.user.UserSignUpSecondFormValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "Signup/registration controller",
    description = "Controller is responsible for two step registration"
)
@RestController
@RequestMapping("/signup")
public class SignUpController {

    private UserService userService;
    private UserSignUpFirstFormValidator userSignupFirstFormValidator;
    private UserSignUpSecondFormValidator userSignUpSecondFormValidator;

    @Autowired
    public void setUserService(
        UserService userService,
        UserSignUpFirstFormValidator userSignupFirstFormValidator,
        UserSignUpSecondFormValidator userSignUpSecondFormValidator
    ) {
        this.userService = userService;
        this.userSignupFirstFormValidator = userSignupFirstFormValidator;
        this.userSignUpSecondFormValidator = userSignUpSecondFormValidator;
    }

    @InitBinder("userSignUpFirstForm")
    private void userSignUpFormBinder(WebDataBinder binder) {
        binder.setValidator(userSignupFirstFormValidator);
    }

    @InitBinder("userSignupSecondForm")
    private void userSignUpSecondFormBinder(WebDataBinder binder) {
        binder.setValidator(userSignUpSecondFormValidator);
    }

    @PostMapping("")
    public AuthUser signup(
        @Validated @RequestBody UserSignUpFirstForm userSignupFormFirst,
        BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return userService.registration(userSignupFormFirst);
    }

    @PostMapping("/name")
    public AuthUser signupNames(
        @Validated @RequestBody UserSignUpSecondForm userSignupSecondForm,
        BindingResult bindingResult,
        @AuthenticationPrincipal AuthUser authUser
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        // TODO: automatic account verification
        return userService.addUserInformation(userSignupSecondForm, authUser);
    }
}
