package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.model.UserModel;
import com.travel.to.travel_to.service.UserToRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserModel> {

    private final UserToRoleService userToRoleService;

    @Autowired
    public UserModelAssembler(
        UserToRoleService userToRoleService
    ) {
        this.userToRoleService = userToRoleService;
    }

    @Override
    @NonNull
    public UserModel toModel(User entity) {
        UserModel userModel = new UserModel();
        userModel
            .setEmail(entity.getEmail())
            .setCreatedAt(entity.getCreatedAt())
            .setName(entity.getName())
            .setSurname(entity.getSurname())
            .setPhone(entity.getPhone())
            .setRole(userToRoleService.getAllUserRolesByUserId(entity.getId()));
        return userModel;
    }

}
