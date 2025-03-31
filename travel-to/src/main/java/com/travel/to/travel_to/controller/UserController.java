package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.assembler.UserModelAssembler;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.model.UserModel;
import com.travel.to.travel_to.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    @Autowired
    public UserController(
        UserService userService,
        UserModelAssembler userModelAssembler
    ) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping("/get")
    public UserModel get(
        @AuthenticationPrincipal AuthUser authUser
    ) {
        return userModelAssembler.toModel(userService.getCurrentUser(authUser));
    }

    @PostMapping("/delete")
    public void deleteUser(
        @AuthenticationPrincipal AuthUser authUser
    ) {
        userService.delete(authUser);
    }

}
