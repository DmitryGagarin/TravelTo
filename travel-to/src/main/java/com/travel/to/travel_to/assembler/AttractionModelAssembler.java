package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.entity.attraction.Attraction;
import com.travel.to.travel_to.model.AttractionModel;
import com.travel.to.travel_to.service.AttractionImageService;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AttractionModelAssembler implements RepresentationModelAssembler<Attraction, AttractionModel> {

    private final AttractionImageService attractionImageService;

    public AttractionModelAssembler(AttractionImageService attractionImageService) {
        this.attractionImageService = attractionImageService;
    }

    @Override
    @NotNull
    public AttractionModel toModel(@NotNull Attraction entity) {
        AttractionModel attractionModel = new AttractionModel();
        attractionModel
            .setUuid(entity.getUuid())
            .setName(entity.getName())
            .setDescription(entity.getDescription())
            .setAddress(entity.getAddress())
            .setImages(attractionImageService.getAllImagesByAttractionId(entity.getId()))
            .setPhone(entity.getPhone())
            .setWebsite(entity.getWebsite())
            .setType(entity.getType())
            .setOpenTime(entity.getOpenTime())
            .setCloseTime(entity.getCloseTime())
            .setRating(entity.getRating());
        return attractionModel;
    }
}
