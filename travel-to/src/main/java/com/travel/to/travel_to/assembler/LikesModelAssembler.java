package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.entity.Likes;
import com.travel.to.travel_to.model.AttractionModel;
import com.travel.to.travel_to.model.LikesModel;
import com.travel.to.travel_to.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LikesModelAssembler implements RepresentationModelAssembler<Likes, LikesModel> {

    private final AttractionService attractionService;
    private final AttractionModelAssembler attractionModelAssembler;

    @Autowired
    public LikesModelAssembler(
        AttractionService attractionService,
        AttractionModelAssembler attractionModelAssembler) {
        this.attractionService = attractionService;
        this.attractionModelAssembler = attractionModelAssembler;
    }

    @Override
    public LikesModel toModel(Likes entity) {
        LikesModel likesModel = new LikesModel();
        List<Attraction> attractions = attractionService.findAllByAttractionId(entity.getAttractionId());
        List<AttractionModel> attractionModels = new ArrayList<>();
        for (Attraction attraction : attractions) {
            attractionModels.add(attractionModelAssembler.toModel(attraction));
        }
        likesModel.setAttraction(attractionModels);
        return likesModel;
    }

}
