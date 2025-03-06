package com.travel.to.travel_to.assemblers;

import com.travel.to.travel_to.entity.User;
import com.travel.to.travel_to.model.UserModel;
import com.travel.to.travel_to.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler {

    private final UserService userService;

    @Autowired
    public UserModelAssembler(
        UserService userService
    ) {
        this.userService = userService;
    }

    @NotNull
    public UserModel toModel(
        @NotNull String uuid
    ) {
        User user = userService.findUserByUuid(uuid);
        UserModel userModel = new UserModel();
        userModel
                .setName(user.getName())
                .setSurname(user.getSurname())
                .setEmail(user.getEmail())
                .setPhone(user.getPhone())
                .setCreatedAt(user.getCreatedAt());

        return userModel;
    }
}
