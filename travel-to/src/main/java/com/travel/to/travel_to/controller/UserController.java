package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(
        UserService userService
    ) {
        this.userService = userService;
    }

    // TODO: не работает удаление
    @PostMapping("/delete")
    public void deleteUser(
        @AuthenticationPrincipal AuthUser authUser
    ) {
        userService.delete(authUser);
    }

}
