package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.user.UserToRole;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public interface UserToRoleService {

    @NotNull
    Set<GrantedAuthority> getAllUserRolesByUserId(@NotNull Long id);

    @NotNull
    UserToRole save(@NotNull UserToRole userToRole);

}
