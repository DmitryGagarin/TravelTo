package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.user.UserToRole;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public interface UserToRoleService {

    @NotNull
    Set<String> getAllUserRolesByUserUuid(@NotNull String uuid);

    @NotNull
    UserToRole save(@NotNull UserToRole userToRole);

}
