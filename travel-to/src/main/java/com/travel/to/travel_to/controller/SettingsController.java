package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.assemblers.UserModelAssembler;
import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.form.UserProfileForm;
import com.travel.to.travel_to.model.UserModel;
import com.travel.to.travel_to.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final UserModelAssembler userModelAssembler;
    private final UserService userService;

    @Autowired
    public SettingsController(
        UserModelAssembler userModelAssembler,
        UserService userService
    ) {
        this.userModelAssembler = userModelAssembler;
        this.userService = userService;
    }

    @GetMapping("/{profile}")
    public UserModel information(
        @AuthenticationPrincipal AuthUser authUser
    ) {
        return userModelAssembler.toModel(authUser.getUuid());
    }

    @PostMapping("/save-changes")
    public User saveChanges(
        @Validated UserProfileForm userProfileForm,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        return userService.saveChanges(userProfileForm, authUser);
    }

}
