package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.entity.attraction.Attraction;
import com.travel.to.travel_to.model.AttractionModel;
import com.travel.to.travel_to.service.AttractionImageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AttractionModelAssembler implements RepresentationModelAssembler<Attraction, AttractionModel> {

    private final AttractionImageService attractionImageService;

    @Autowired
    public AttractionModelAssembler(
        AttractionImageService attractionImageService
    ) {
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
            .setImagesFormats(attractionImageService.getAllImagesFormatsByAttractionId(entity.getId()))
            .setPhone(entity.getPhone())
            .setWebsite(entity.getWebsite())
            .setType(entity.getType())
            .setOpenTime(entity.getOpenTime())
            .setCloseTime(entity.getCloseTime())
            .setRating(entity.getRating())
            .setStatus(entity.getStatus());
        return attractionModel;
    }
}