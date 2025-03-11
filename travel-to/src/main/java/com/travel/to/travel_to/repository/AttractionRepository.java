package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.entity.AttractionType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    @NotNull
    Page<Attraction> getAllByType(@NotNull AttractionType type, @NotNull Pageable pageable);

    @NotNull
    Page<Attraction> findAll(@NotNull Pageable pageable);

}
