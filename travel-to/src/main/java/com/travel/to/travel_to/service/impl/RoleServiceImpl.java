package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.user.Role;
import com.travel.to.travel_to.repository.RoleRepository;
import com.travel.to.travel_to.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.getRoleByRoleName(name);
    }

    @Override
    public Set<Role> getAllRoles() {
        return roleRepository.getAllRoles();
    }
}
