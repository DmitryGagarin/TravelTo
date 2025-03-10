package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.validator.UserProfileFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final UserService userService;
    private final UserProfileFormValidator userProfileFormValidator;

    @Autowired
    public SettingsController(
        UserService userService,
        UserProfileFormValidator userProfileFormValidator
    ) {
        this.userService = userService;
        this.userProfileFormValidator = userProfileFormValidator;
    }

    @InitBinder
    public void userProfileFormValidatorBinder(WebDataBinder binder) {
        binder.addValidators(userProfileFormValidator);
    }

    @PostMapping("/save-changes")
    public User saveChanges(
        @Validated @RequestBody UserProfileForm userProfileForm,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        return userService.saveChanges(userProfileForm, authUser);
    }
}
