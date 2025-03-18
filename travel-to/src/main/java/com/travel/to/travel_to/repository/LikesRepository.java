package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.entity.Likes;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    @NotNull
    List<Likes> findAllByUserId(Long userId);

}
