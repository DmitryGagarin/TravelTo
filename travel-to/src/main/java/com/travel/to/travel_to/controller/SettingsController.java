package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.validator.user.UserProfileFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/settings")
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

    @InitBinder("userProfileBinder")
    public void userProfileFormValidatorBinder(WebDataBinder binder) {
        binder.addValidators(userProfileFormValidator);
    }

    @PostMapping("/save-changes")
    public User saveChanges(
        @Validated @RequestBody @ModelAttribute("userProfileBinder") UserProfileForm userProfileForm,
        BindingResult bindingResult,
        @AuthenticationPrincipal AuthUser authUser
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return userService.saveChanges(userProfileForm, authUser);
    }

}
