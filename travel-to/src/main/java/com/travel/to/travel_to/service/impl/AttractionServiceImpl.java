package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.entity.AttractionType;
import com.travel.to.travel_to.form.AttractionCreateForm;
import com.travel.to.travel_to.repository.AttractionRepository;
import com.travel.to.travel_to.service.AttractionService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;

    @Autowired
    public AttractionServiceImpl(
        AttractionRepository attractionRepository
    ) {
        this.attractionRepository = attractionRepository;
    }

    @Override
    @NotNull
    public Page<Attraction> getAllByType(@NotNull AttractionType type, @NotNull Pageable pageable) {
        return attractionRepository.getAllByType(type, pageable);
    }

    @Override
    public Page<Attraction> findAll(Pageable pageable) {
        return attractionRepository.findAll(pageable);
    }

    @Override
    @NotNull
    @Transactional
    public Attraction createAttraction(AttractionCreateForm attractionCreateForm) {
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
        return attractionRepository.save(attraction);
    }

    @Override
    public Optional<Attraction> findById(Long id) {
        return attractionRepository.findById(id);
    }
}
