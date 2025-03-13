package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.model.AttractionModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AttractionModelAssembler implements RepresentationModelAssembler<Attraction, AttractionModel> {

    @Override
    public AttractionModel toModel(Attraction entity) {
        return null;
    }

    @Override
    public CollectionModel<AttractionModel> toCollectionModel(Iterable<? extends Attraction> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
