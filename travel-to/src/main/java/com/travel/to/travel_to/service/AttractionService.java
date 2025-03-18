package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.AttractionCreateForm;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AttractionService {

    @NotNull
    List<Attraction> findAll();

    @NotNull
    List<Attraction> findAllByOwner(@NotNull AuthUser authUser);

    @NotNull
    Attraction createAttraction(
        @NotNull AttractionCreateForm attractionCreateForm,
        @NotNull AuthUser authUser,
        @NotNull MultipartFile image
    );

    @NotNull
    Attraction updateRating(
        @NotNull String attractionUuid,
        @NotNull Double totalRating
    );

    Optional<Attraction> findByName(@NotNull String name);

    @NotNull
    Attraction getByUuid(@NotNull String uuid);

    @NotNull
    Attraction getByName(@NotNull String name);

    @NotNull
    List<Double> findAllAttractionRatingByAttractionId(Long attractionId);
}
