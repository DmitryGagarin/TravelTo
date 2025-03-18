package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.constants.DefaultInitialValues;
import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.AttractionCreateForm;
import com.travel.to.travel_to.repository.AttractionRepository;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;
    private final UserService userService;

    @Autowired
    public AttractionServiceImpl(
        AttractionRepository attractionRepository,
        UserService userService) {
        this.attractionRepository = attractionRepository;
        this.userService = userService;
    }

    @Override
    public List<Attraction> findAll() {
        return attractionRepository.findAll();
    }

    @Override
    public List<Attraction> findAllByOwner(AuthUser authUser) {
        return attractionRepository.findAllByOwnerId(userService.findByUuid(authUser.getUuid()).getId());
    }

    @Override
    @NotNull
    public Attraction createAttraction(
        @NotNull AttractionCreateForm attractionCreateForm,
        @NotNull AuthUser authUser,
        @NotNull MultipartFile image
    ) {
        byte[] imageBytes;
        try {
            imageBytes = image.getBytes();
        } catch (IOException ignored) {
            throw new RuntimeException("Can't get bytes of image");
        }

        // TODO: менять тип юзера при создании им достопремечательности
        Attraction attraction = new Attraction();
        attraction
            .setName(attractionCreateForm.getName())
            .setDescription(attractionCreateForm.getDescription())
            .setAddress(attractionCreateForm.getAddress())
            .setImage(imageBytes)
            .setPhone(attractionCreateForm.getPhone())
            .setWebsite(attractionCreateForm.getWebsite())
            .setOpenTime(attractionCreateForm.getOpenTime())
            .setCloseTime(attractionCreateForm.getCloseTime())
            .setType(attractionCreateForm.getAttractionType())
            .setRating(DefaultInitialValues.INITIAL_ATTRACTION_RATING)
            .setOwner(userService.findByUuid(authUser.getUuid()));
        return attractionRepository.save(attraction);
    }

    @Override
    public Attraction updateRating(
        @NotNull String attractionUuid,
        @NotNull Double totalRating
    ) {
        Attraction attraction = getByUuid(attractionUuid);
        attraction.setRating(totalRating);
        return attractionRepository.save(attraction);
    }

    @Override
    public Optional<Attraction> findByName(@NotNull String attractionName) {
        return attractionRepository.findByName(attractionName);
    }

    @Override
    public Attraction getByUuid(@NotNull String uuid) {
        return attractionRepository.getByUuid(uuid);
    }

    @Override
    public Attraction getByName(String attractionName) {
        return attractionRepository.getByName(attractionName);
    }

    @Override
    @NotNull
    public List<Double> findAllAttractionRatingByAttractionId(Long attractionId) {
        return attractionRepository.findAllAttractionRatingsById(attractionId);
    }

}
