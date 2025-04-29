package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.attraction.Attraction;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    List<Attraction> findAllByStatus(@NotNull String status);

    List<Attraction> findAllByOrderByPriorityDesc();

    @NotNull
    @Query(value = "SELECT * FROM attraction a WHERE a.owner_id = :ownerId",
    nativeQuery = true)
    List<Attraction> findAllByOwnerId(@NotNull Long ownerId);

    @NotNull
    @Query(value = "SELECT a.rating FROM attraction a WHERE a.id = :attractionId",
    nativeQuery = true)
    List<Double> findAllAttractionRatingsById(Long attractionId);

    Optional<Attraction> findByName(@NotNull String name);

    @NotNull
    Attraction getByUuid(@NotNull String uuid);

    @NotNull
    Attraction getByName(@NotNull String name);
}
