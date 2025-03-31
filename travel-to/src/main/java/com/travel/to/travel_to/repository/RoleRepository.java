package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM role WHERE role.role = :role",
    nativeQuery = true)
    Role getRoleByRoleName(String role);

    @Query(value = "SELECT * FROM role",
    nativeQuery = true)
    Set<Role> getAllRoles();
}
