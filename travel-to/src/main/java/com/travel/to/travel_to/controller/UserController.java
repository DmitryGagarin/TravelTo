package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{uuid}")
    public User information(
        @PathVariable String uuid
    ) {
        return userService.findUserByUuid(uuid);
    }


}
