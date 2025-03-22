package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.constants.DefaultInitialValues;
import com.travel.to.travel_to.entity.attraction.Attraction;
import com.travel.to.travel_to.entity.attraction.AttractionStatus;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.entity.user.UserType;
import com.travel.to.travel_to.form.AttractionCreateForm;
import com.travel.to.travel_to.repository.AttractionRepository;
import com.travel.to.travel_to.service.AttractionImageService;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;
    private final UserService userService;
    private final AttractionImageService attractionImageService;

    @Autowired
    public AttractionServiceImpl(
        AttractionRepository attractionRepository,
        UserService userService,
        AttractionImageService attractionImageService
    ) {
        this.attractionRepository = attractionRepository;
        this.userService = userService;
        this.attractionImageService = attractionImageService;
    }

    @Override
    @NotNull
    public List<Attraction> findAll() {
        return attractionRepository.findAll();
    }

    @Override
    @NotNull
    public List<Attraction> findAllByStatus(@NotNull AttractionStatus status) {
        return attractionRepository.findAllByStatus(status);
    }

    @Override
    @NotNull
    public List<Attraction> findAllByAttractionId(@NotNull Long attractionId) {
        return attractionRepository.findAllById(Collections.singleton(attractionId));
    }

    @Override
    @NotNull
    public List<Attraction> findAllByOwner(@NotNull AuthUser authUser) {
        return attractionRepository.findAllByOwnerId(userService.findByUuid(authUser.getUuid()).getId());
    }

    @Override
    @NotNull
    public List<Double> findAllAttractionRatingByAttractionId(@NotNull Long attractionId) {
        return attractionRepository.findAllAttractionRatingsById(attractionId);
    }

    @Override
    @NotNull
    public Attraction createAttraction(
        @NotNull AttractionCreateForm attractionCreateForm,
        @NotNull AuthUser authUser,
        @NotNull MultipartFile[] images
    ) {
        userService.updateUserType(authUser, UserType.BUSINESS_OWNER);

        Attraction attraction = new Attraction();
        attraction
            .setName(attractionCreateForm.getName())
            .setDescription(attractionCreateForm.getDescription())
            .setAddress(attractionCreateForm.getAddress())
            .setPhone(attractionCreateForm.getPhone())
            .setWebsite(attractionCreateForm.getWebsite())
            .setOpenTime(attractionCreateForm.getOpenTime())
            .setCloseTime(attractionCreateForm.getCloseTime())
            .setType(attractionCreateForm.getAttractionType())
            .setRating(DefaultInitialValues.INITIAL_ATTRACTION_RATING)
            .setOwner(userService.findByUuid(authUser.getUuid()))
            .setStatus(AttractionStatus.ON_MODERATION);
        attractionRepository.save(attraction);

        try {
            attractionImageService.create(images, attraction.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return attraction;
    }

    @Override
    @NotNull
    public Attraction updateRating(
        @NotNull String attractionUuid,
        @NotNull Double totalRating
    ) {
        Attraction attraction = getByUuid(attractionUuid);
        attraction.setRating(totalRating);
        return attractionRepository.save(attraction);
    }

    @Override
    @NotNull
    public Attraction updateAttractionStatus(
        @NotNull AttractionStatus attractionStatus,
        @NotNull String attractionName
    ) {
        Attraction attraction = getByName(attractionName);
        attraction.setStatus(attractionStatus);
        return attractionRepository.save(attraction);
    }

    @Override
    @NotNull
    public Attraction getByUuid(@NotNull String uuid) {
        return attractionRepository.getByUuid(uuid);
    }

    @Override
    @NotNull
    public Attraction getByName(@NotNull String attractionName) {
        return attractionRepository.getByName(attractionName);
    }

    @Override
    public Optional<Attraction> findByName(@NotNull String attractionName) {
        return attractionRepository.findByName(attractionName);
    }

    @Override
    public void deleteAttractionByName(
        @NotNull String name,
        @NotNull AuthUser authUser
    ) {
        attractionRepository.delete(getByName(name));
        if (findAllByOwner(authUser).isEmpty()) {
            User user = userService.findByUuid(authUser.getUuid());
            userService.updateUserType(authUser, UserType.VISITOR);
        }
    }
}
