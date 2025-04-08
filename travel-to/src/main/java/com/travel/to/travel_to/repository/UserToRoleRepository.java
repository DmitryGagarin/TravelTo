package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.user.UserToRole;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserToRoleRepository extends JpaRepository<UserToRole, Long> {

    @NotNull
    @Query(value = "SELECT user_to_role.role_id FROM user_to_role where user_id = :id",
        nativeQuery = true)
    List<String> getUserRolesByUserId(@NotNull Long id);

}
