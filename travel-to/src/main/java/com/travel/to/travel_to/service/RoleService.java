package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.user.Role;

import java.util.Set;

public interface RoleService {
    Role getRoleByName(String name);

    Set<Role> getAllRoles();
}
