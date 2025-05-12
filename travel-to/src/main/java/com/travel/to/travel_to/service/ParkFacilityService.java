package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.attraction_feature.facilities.Facility;
import com.travel.to.travel_to.form.attraction_feature.ParkFacilityCreateForm;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ParkFacilityService {

    @NotNull
    List<Facility> createFacility(
        @NotNull MultipartFile[] images,
        @NotNull String attractionName,
        @NotNull ParkFacilityCreateForm parkFacilityCreateForm
    ) throws IOException;

    Optional<List<Facility>> getFacilityByAttractionName(
        @NotNull String attractionName
    );

}
