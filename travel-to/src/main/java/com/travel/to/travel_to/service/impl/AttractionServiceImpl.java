package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.constants.DefaultInitialValues;
import com.travel.to.travel_to.entity.attraction.Attraction;
import com.travel.to.travel_to.entity.attraction.AttractionStatus;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.Roles;
import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.form.attraction.AttractionCreateForm;
import com.travel.to.travel_to.form.attraction.AttractionEditForm;
import com.travel.to.travel_to.repository.AttractionRepository;
import com.travel.to.travel_to.service.AttractionImageService;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.UserService;
import com.travel.to.travel_to.cache.AttractionCacheUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final AttractionCacheUtil attractionCacheUtil;

    @Autowired
    public AttractionServiceImpl(
        AttractionRepository attractionRepository,
        UserService userService,
        AttractionImageService attractionImageService,
        AttractionCacheUtil attractionCacheUtil
    ) {
        this.attractionRepository = attractionRepository;
        this.userService = userService;
        this.attractionImageService = attractionImageService;
        this.attractionCacheUtil = attractionCacheUtil;
    }

    @Override
    @NotNull
    public List<Attraction> findAllByPriorityDesc() {
        return attractionRepository.findAllByOrderByPriorityDesc();
    }

    @Override
    @NotNull
    public List<Attraction> findAllByStatus(@NotNull String status) {
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
        return attractionRepository.findAllByOwnerId(userService.getByUuid(authUser.getUuid()).getId());
    }

    @Override
    @NotNull
    public List<Double> findAllAttractionRatingByAttractionId(@NotNull Long attractionId) {
        return attractionRepository.findAllAttractionRatingsById(attractionId);
    }

    @Override
    @NotNull
    @Transactional
    public Attraction createAttraction(
        @NotNull AttractionCreateForm attractionCreateForm,
        @NotNull AuthUser authUser,
        @NotNull MultipartFile[] images
    ) {
        if (findAllByOwner(authUser).isEmpty()) {
            userService.updateUserRole(authUser, Roles.OWNER);
        }

        Attraction attraction = new Attraction();
        attraction
            .setOwnerTelegram(attractionCreateForm.getOwnerTelegram())
            .setName(attractionCreateForm.getAttractionName())
            .setDescription(attractionCreateForm.getDescription())
            .setAddress(attractionCreateForm.getAddress())
            .setPhone(attractionCreateForm.getPhone())
            .setWebsite(attractionCreateForm.getWebsite())
            .setOpenTime(attractionCreateForm.getOpenTime())
            .setCloseTime(attractionCreateForm.getCloseTime())
            .setType(attractionCreateForm.getAttractionType())
            .setRating(DefaultInitialValues.INITIAL_ATTRACTION_RATING)
            .setOwner(userService.getByUuid(authUser.getUuid()))
            .setStatus(AttractionStatus.on_moderation.name())
            .setPriority(DefaultInitialValues.INITIAL_ATTRACTION_PRIORITY);
        attractionRepository.save(attraction);
        attractionCacheUtil.save(attraction);

        try {
            attractionImageService.save(images, attraction.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return attraction;
    }

    @Override
    @NotNull
    @Transactional
    public Attraction editAttraction(
        @NotNull AttractionEditForm attractionEditForm,
        @NotNull MultipartFile[] images,
        @NotNull String currentName
    ) {
        Attraction attraction = getByName(currentName);
        attraction
            .setName(attractionEditForm.getAttractionName())
            .setDescription(attractionEditForm.getDescription())
            .setAddress(attractionEditForm.getAddress())
            .setPhone(attractionEditForm.getPhone())
            .setWebsite(attractionEditForm.getWebsite())
            .setType(attractionEditForm.getAttractionType())
            .setOpenTime(attractionEditForm.getOpenTime())
            .setCloseTime(attractionEditForm.getCloseTime());

        attractionCacheUtil.updateById(attraction.getId(), attraction);

        try {
            attractionImageService.edit(images, attraction.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return attractionRepository.save(attraction);
    }

    @Override
    @NotNull
    public Attraction updateRating(
        @NotNull String attractionUuid,
        @NotNull Double totalRating
    ) {
        Attraction attraction = getByUuid(attractionUuid);
        attraction.setRating(totalRating);
        attractionCacheUtil.updateById(attraction.getId(), attraction);
        return attractionRepository.save(attraction);
    }

    @Override
    @NotNull
    public Attraction updateAttractionStatus(
        @NotNull AttractionStatus attractionStatus,
        @NotNull String attractionName
    ) {
        Attraction attraction = getByName(attractionName);
        attraction.setStatus(attractionStatus.name());
        attractionCacheUtil.updateById(attraction.getId(), attraction);
        return attractionRepository.save(attraction);
    }

    @Override
    @NotNull
    public Attraction getByUuid(@NotNull String uuid) {
        return attractionRepository.getByUuid(uuid);
    }

    @Override
    @NotNull
    public Attraction getById(@NotNull Long attractionId) {
        return attractionRepository.getById(attractionId);
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
    @NotNull
    public String getTypeByName(@NotNull String name) {
        return getByName(name).getType();
    }

    @Override
    public void deleteAttractionByName(
        @NotNull String name,
        @NotNull AuthUser authUser
    ) {
        attractionRepository.delete(getByName(name));
        attractionCacheUtil.deleteById(getByName(name).getId());
        if (findAllByOwner(authUser).isEmpty()) {
            User user = userService.getByUuid(authUser.getUuid());
            userService.updateUserRole(authUser, Roles.USER);
        }
    }
}
