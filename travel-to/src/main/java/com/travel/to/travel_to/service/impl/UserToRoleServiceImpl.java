package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.user.UserToRole;
import com.travel.to.travel_to.repository.UserToRoleRepository;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.service.UserToRoleService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserToRoleServiceImpl implements UserToRoleService {

    private final UserToRoleRepository userToRoleRepository;
    private final UserService userService;

    @Autowired
    public UserToRoleServiceImpl(
        UserToRoleRepository userToRoleRepository,
        UserService userService
    ) {
        this.userToRoleRepository = userToRoleRepository;
        this.userService = userService;
    }

    @Override
    @NotNull
    public Set<String> getAllUserRolesByUserUuid(@NotNull String uuid) {
        return userToRoleRepository.getUserRolesByUserId(userService.findByUuid(uuid).getId());
    }

    @Override
    @NotNull
    public UserToRole save(
        @NotNull UserToRole userToRole
    ) {
        return userToRoleRepository.save(userToRole);
    }

}
