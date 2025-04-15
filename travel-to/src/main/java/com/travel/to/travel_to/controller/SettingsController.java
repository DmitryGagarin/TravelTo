package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.validator.user.UserProfileFormValidator;
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
    name = "Settings controller",
    description = "Controller is responsible for settings actions"
)
@RestController
@RequestMapping("/setting")
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

    // TODO: если что-то меняется, то надо сделать автоперезаход или как-то пофиксить проблему
    @PostMapping("/save-changes")
    public AuthUser saveChanges(
        @Validated @RequestBody UserProfileForm userProfileForm,
        BindingResult bindingResult,
        @AuthenticationPrincipal AuthUser authUser
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return userService.saveChanges(userProfileForm, authUser);
    }

}
