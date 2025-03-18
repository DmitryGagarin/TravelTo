package com.travel.to.travel_to.model;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class LikesModel extends RepresentationModel<LikesModel> {
    private List<AttractionModel> attraction;

    public List<AttractionModel> getAttraction() {
        return attraction;
    }

    public LikesModel setAttraction(List<AttractionModel> attraction) {
        this.attraction = attraction;
        return this;
    }
}
