package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.user.UserToRole;
import com.travel.to.travel_to.repository.UserToRoleRepository;
import com.travel.to.travel_to.service.UserToRoleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserToRoleServiceImpl implements UserToRoleService {

    private final UserToRoleRepository userToRoleRepository;

    @Autowired
    public UserToRoleServiceImpl(
        UserToRoleRepository userToRoleRepository
    ) {
        this.userToRoleRepository = userToRoleRepository;
    }

    @Override
    @NotNull
    public Set<GrantedAuthority> getAllUserRolesByUserId(@NotNull Long id) {
        Set<GrantedAuthority> roles = new LinkedHashSet<>();
        List<String> rolesIds = userToRoleRepository.getUserRolesByUserId(id);
        for (String roleId : rolesIds) {
            switch (roleId) {
                case "1" -> roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                case "2" -> roles.add(new SimpleGrantedAuthority("ROLE_OWNER"));
                case "3" -> roles.add(new SimpleGrantedAuthority("ROLE_USER"));
                case "4" -> roles.add(new SimpleGrantedAuthority("ROLE_DISCUSSION_OWNER"));
            }
        }
        return roles;
    }

    @Override
    @NotNull
    public UserToRole save(
        @NotNull UserToRole userToRole
    ) {
        return userToRoleRepository.save(userToRole);
    }

}
