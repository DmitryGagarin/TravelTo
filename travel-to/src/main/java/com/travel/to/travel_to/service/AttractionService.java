package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.entity.AttractionType;
import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.AttractionCreateForm;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AttractionService {

    @NotNull
    Page<Attraction> getAllByType(@NotNull AttractionType type, Pageable pageable);

    @NotNull
    List<Attraction> findAll();

    @NotNull
    Attraction createAttraction(
        @NotNull AttractionCreateForm attractionCreateForm,
        @NotNull AuthUser authUser
    );

    Optional<Attraction> findById(@NotNull Long id);
}
