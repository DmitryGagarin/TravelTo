package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.entity.AttractionType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AttractionService {

    @NotNull
    Page<Attraction> getAllByType(@NotNull AttractionType type, Pageable pageable);

    Optional<Attraction> findById(@NotNull Long id);
}
