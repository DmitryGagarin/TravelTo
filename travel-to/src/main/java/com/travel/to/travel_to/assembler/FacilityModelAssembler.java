package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.entity.attraction_feature.facilities.Facility;
import com.travel.to.travel_to.model.FacilityModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FacilityModelAssembler implements RepresentationModelAssembler<Facility, FacilityModel> {

    public Optional<List<FacilityModel>> toOptionalModels(
        Optional<List<Facility>> facilities
    ) {
        if (facilities.isEmpty()) {
            return Optional.empty();
        }
        List<FacilityModel> facilityModels = new ArrayList<>();
        for (Facility facility : facilities.get()) {
            facilityModels.add(toModel(facility));
        }
        return Optional.of(facilityModels);
    }

    public List<FacilityModel> toModels(List<Facility> facilities) {
        List<FacilityModel> facilityModels = new ArrayList<>();
        for (Facility facility : facilities) {
            facilityModels.add(toModel(facility));
        }
        return facilityModels;
    }

    @Override
    @NotNull
    public FacilityModel toModel(@NotNull Facility entity) {
        return (
            new FacilityModel()
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setOpenTime(entity.getOpenTime())
                .setCloseTime(entity.getCloseTime())
                .setImage(entity.getImage())
                .setImageFormat(entity.getImageFormat())
        );
    }

}
