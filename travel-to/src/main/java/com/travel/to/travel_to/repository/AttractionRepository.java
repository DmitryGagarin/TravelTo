package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.Attraction;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    @NotNull
    List<Attraction> findAll();

    Optional<Attraction> findByName(@NotNull String name);

}
