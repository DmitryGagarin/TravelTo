package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.constants.FormatConstants;
import com.travel.to.travel_to.entity.attraction.AttractionDiscussion;
import com.travel.to.travel_to.model.AttractionDiscussionModel;
import com.travel.to.travel_to.service.AttractionDiscussionImageService;
import com.travel.to.travel_to.service.UserService;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AttractionDiscussionModelAssembler implements RepresentationModelAssembler<AttractionDiscussion, AttractionDiscussionModel> {

    private final UserService userService;
    private final AttractionDiscussionImageService attractionDiscussionImageService;

    @Autowired
    public AttractionDiscussionModelAssembler(
        UserService userService,
        AttractionDiscussionImageService attractionDiscussionImageService) {
        this.userService = userService;
        this.attractionDiscussionImageService = attractionDiscussionImageService;
    }

    @Override
    @Nonnull
    public AttractionDiscussionModel toModel(@NotNull AttractionDiscussion entity) {

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
            .setCreatedAt(FormatConstants.DAY_MONTH_YEAR_FORMATTER.format(entity.getCreatedAt()))
            .setImages(attractionDiscussionImageService.getAllImagesByDiscussionId(entity.getId()));
        return attractionDiscussionModel;
    }
}