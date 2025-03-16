package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.entity.AttractionDiscussion;
import com.travel.to.travel_to.model.AttractionDiscussionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AttractionDiscussionModelAssembler implements RepresentationModelAssembler<AttractionDiscussion, AttractionDiscussionModel> {

    @Override
    public AttractionDiscussionModel toModel(AttractionDiscussion entity) {
        AttractionDiscussionModel attractionDiscussionModel = new AttractionDiscussionModel();
        attractionDiscussionModel
            .setTitle(entity.getTitle())
            .setContentLike(entity.getContentLike())
            .setContentDislike(entity.getContentDislike())
            .setContent(entity.getContent())
            .setRating(entity.getRating());
        return attractionDiscussionModel;
    }

}
