package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.entity.AttractionDiscussion;
import com.travel.to.travel_to.model.AttractionDiscussionModel;
import com.travel.to.travel_to.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AttractionDiscussionModelAssembler implements RepresentationModelAssembler<AttractionDiscussion, AttractionDiscussionModel> {

    private final UserService userService;

    @Autowired
    public AttractionDiscussionModelAssembler(
        UserService userService
    ) {
        this.userService = userService;
    }

    @Override
    public AttractionDiscussionModel toModel(AttractionDiscussion entity) {

        String authorName;
        if (userService.findById(entity.getAttractionId()).isPresent()) {
            authorName =
                userService.findById(entity.getAttractionId()).get().getName() +
                " " +
                userService.findById(entity.getAttractionId()).get().getSurname();
        } else {
            authorName = "Default Author";
        }

        AttractionDiscussionModel attractionDiscussionModel = new AttractionDiscussionModel();
        attractionDiscussionModel
            .setTitle(entity.getTitle())
            .setContentLike(entity.getContentLike())
            .setContentDislike(entity.getContentDislike())
            .setContent(entity.getContent())
            .setRating(entity.getRating())
            .setAuthor(authorName)
            // TODO: return normalized time
            .setCreatedAt(entity.getCreatedAt());
        return attractionDiscussionModel;
    }

}
