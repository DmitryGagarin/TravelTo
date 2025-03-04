package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @NotNull
    User findUserByUuid(@NotNull String uuid);
}
