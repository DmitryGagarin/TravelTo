package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.attraction.Attraction;
import com.travel.to.travel_to.entity.attraction.AttractionStatus;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.attraction.AttractionCreateForm;
import com.travel.to.travel_to.form.attraction.AttractionEditForm;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AttractionService {

    @NotNull
    List<Attraction> findAllByPriorityDesc();

    @NotNull
    List<Attraction> findAllByStatus(@NotNull String status);

    @NotNull
    List<Attraction> findAllByAttractionId(@NotNull Long attractionId);

    @NotNull
    List<Attraction> findAllByOwner(@NotNull AuthUser authUser);

    @NotNull
    List<Double> findAllAttractionRatingByAttractionId(@NotNull Long attractionId);

    @NotNull
    Attraction createAttraction(
        @NotNull AttractionCreateForm attractionCreateForm,
        @NotNull AuthUser authUser,
        @NotNull MultipartFile[] image
    );

    @NotNull
    Attraction editAttraction(
        @NotNull AttractionEditForm attractionEditForm,
        @NotNull MultipartFile[] images,
        @NotNull String currentName
    );

    @NotNull
    Attraction updateRating(
        @NotNull String attractionUuid,
        @NotNull Double totalRating
    );

    @NotNull
    Attraction updateAttractionStatus(
        @NotNull AttractionStatus attractionStatus,
        @NotNull String name
    );

    @NotNull
    Attraction getByUuid(@NotNull String uuid);

    @NotNull
    Attraction getByName(@NotNull String name);

    Optional<Attraction> findByName(@NotNull String name);

    @NotNull
    String getTypeByName(@NotNull String name);

    void deleteAttractionByName(@NotNull String name, @NotNull AuthUser authUser);
}
