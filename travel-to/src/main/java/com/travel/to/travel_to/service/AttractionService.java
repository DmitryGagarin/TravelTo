package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.AttractionCreateForm;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface AttractionService {

    @NotNull
    List<Attraction> findAll();

    @NotNull
    Attraction createAttraction(
        @NotNull AttractionCreateForm attractionCreateForm,
        @NotNull AuthUser authUser
    );

    Optional<Attraction> findByName(@NotNull String name);
}
