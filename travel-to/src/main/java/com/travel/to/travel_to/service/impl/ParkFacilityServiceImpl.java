package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.attraction_feature.facilities.Facility;
import com.travel.to.travel_to.form.attraction_feature.ParkFacilityCreateForm;
import com.travel.to.travel_to.repository.ParkFacilityRepository;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.ParkFacilityService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.travel.to.travel_to.utils.ImageUtils.getImageFormat;

@Service
public class ParkFacilityServiceImpl implements ParkFacilityService {

    private final ParkFacilityRepository parkFacilityRepository;
    private final AttractionService attractionService;

    @Autowired
    public ParkFacilityServiceImpl(
        ParkFacilityRepository parkFacilityRepository,
        AttractionService attractionService
    ) {
        this.parkFacilityRepository = parkFacilityRepository;
        this.attractionService = attractionService;
    }

    @Override
    @NotNull
    public List<Facility> createFacility(
        @NotNull MultipartFile[] images,
        @NotNull String attractionName,
        @NotNull ParkFacilityCreateForm parkFacilityCreateForm
    ) throws IOException {
        List<Facility> facilities = new ArrayList<>();
        for (int i = 0; i < parkFacilityCreateForm.getNames().size(); i++) {
            Facility facility = new Facility();
            facility
                .setName(parkFacilityCreateForm.getNames().get(i))
                .setDescription(parkFacilityCreateForm.getDescriptions().get(i))
                .setOpenTime(parkFacilityCreateForm.getOpenTimes().get(i))
                .setCloseTime(parkFacilityCreateForm.getCloseTimes().get(i))
                .setAttractionId(attractionService.getByName(attractionName).getId());
            if (Objects.nonNull(images)) {
                facility
                    .setImage(images[i].getBytes())
                    .setImageFormat(getImageFormat(images[i]));
            }
            facilities.add(facility);
        }

        return parkFacilityRepository.saveAll(facilities);
    }

    @Override
    public Optional<List<Facility>> getFacilityByAttractionName(@NotNull String attractionName) {
        return parkFacilityRepository.findAllByAttractionId(
            attractionService.getByName(attractionName).getId()
        );
    }
}
