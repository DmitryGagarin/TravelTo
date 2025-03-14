package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.constants.DefaultInitialValues;
import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.entity.AttractionType;
import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.AttractionCreateForm;
import com.travel.to.travel_to.repository.AttractionRepository;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @NotNull
    @Transactional
    public Attraction createAttraction(
        @NotNull AttractionCreateForm attractionCreateForm,
        @NotNull AuthUser authUser
    ) {
        Attraction attraction = new Attraction();
        attraction.setName(attractionCreateForm.getName());
        attraction.setDescription(attractionCreateForm.getDescription());
        attraction.setAddress(attractionCreateForm.getAddress());
        attraction.setImage(attractionCreateForm.getImage());
        attraction.setPhone(attractionCreateForm.getPhone());
        attraction.setWebsite(attractionCreateForm.getWebsite());
        attraction.setOpenTime(attractionCreateForm.getOpenTime());
        attraction.setCloseTime(attractionCreateForm.getCloseTime());
        attraction.setType(AttractionType.RELIGIOUS.name());
        attraction.setOwner(userService.findByUuid(authUser.getUuid()));
        attraction.setRating(DefaultInitialValues.INITIAL_ATTRACTION_RATING);
        return attractionRepository.save(attraction);
    }

    @Override
    public Optional<Attraction> findByName(@NotNull String attractionName) {
        return attractionRepository.findByName(attractionName);
    }
}
