package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.AttractionCreateForm;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.validator.AttractionCreateFormValidator;
import com.travel.to.travel_to.validator.UserProfileFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
public class SettingsController {

    private final UserService userService;
    private final UserProfileFormValidator userProfileFormValidator;
    private final AttractionCreateFormValidator attractionCreateFormValidator;
    private final AttractionService attractionService;

    @Autowired
    public SettingsController(
        UserService userService,
        UserProfileFormValidator userProfileFormValidator,
        AttractionCreateFormValidator attractionCreateFormValidator,
        AttractionService attractionService
    ) {
        this.userService = userService;
        this.userProfileFormValidator = userProfileFormValidator;
        this.attractionCreateFormValidator = attractionCreateFormValidator;
        this.attractionService = attractionService;
    }

    @InitBinder("userProfileBinder")
    public void userProfileFormValidatorBinder(WebDataBinder binder) {
        binder.addValidators(userProfileFormValidator);
    }

    @InitBinder("registerBusinessBinder")
    public void attractionFormValidatorBinder(WebDataBinder binder) {
        binder.addValidators(attractionCreateFormValidator);
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

    @PostMapping("/register-business")
    public Attraction registerBusiness(
        @Validated @RequestBody AttractionCreateForm attractionCreateForm,
        BindingResult bindingResult,
        @AuthenticationPrincipal AuthUser authUser
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return attractionService.createAttraction(attractionCreateForm);
    }
}
