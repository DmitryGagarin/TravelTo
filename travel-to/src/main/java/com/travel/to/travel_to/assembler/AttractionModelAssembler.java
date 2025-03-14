package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.model.AttractionModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AttractionModelAssembler implements RepresentationModelAssembler<Attraction, AttractionModel> {

    @Override
    @NotNull
    public AttractionModel toModel(@NotNull Attraction entity) {
        AttractionModel attractionModel = new AttractionModel();
        attractionModel
            .setName(entity.getName())
            .setDescription(entity.getDescription())
            .setAddress(entity.getAddress())
            .setImage(entity.getImage())
            .setPhone(entity.getPhone())
            .setWebsite(entity.getWebsite())
            .setType(entity.getType())
            .setOpenTime(entity.getOpenTime())
            .setCloseTime(entity.getCloseTime())
            .setRating(entity.getRating());
        return attractionModel;
    }
}
