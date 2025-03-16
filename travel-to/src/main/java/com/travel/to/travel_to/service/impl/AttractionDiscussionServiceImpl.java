package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.AttractionDiscussion;
import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.CreateAttractionDiscussionForm;
import com.travel.to.travel_to.repository.AttractionDiscussionRepository;
import com.travel.to.travel_to.service.AttractionDiscussionService;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttractionDiscussionServiceImpl implements AttractionDiscussionService {

    private final UserService userService;
    private final AttractionService attractionService;
    private final AttractionDiscussionRepository attractionDiscussionRepository;

    @Autowired
    public AttractionDiscussionServiceImpl(
        UserService userService,
        AttractionService attractionService,
        AttractionDiscussionRepository attractionDiscussionRepository
    ) {
        this.userService = userService;
        this.attractionService = attractionService;
        this.attractionDiscussionRepository = attractionDiscussionRepository;
    }

    @Override
    public AttractionDiscussion create(
        @NotNull CreateAttractionDiscussionForm createAttractionDiscussionForm,
        @NotNull AuthUser authUser,
        @NotNull String attractionUuid
    ) {
        AttractionDiscussion attractionDiscussion = new AttractionDiscussion();
        attractionDiscussion
            .setTitle(createAttractionDiscussionForm.getTitle())
            .setContentLike(createAttractionDiscussionForm.getContentLike())
            .setContentDislike(createAttractionDiscussionForm.getContentDislike())
            .setContent(createAttractionDiscussionForm.getContent())
            .setRating(createAttractionDiscussionForm.getRating())
            .setAuthorId(userService.findByUuid(authUser.getUuid()))
            .setAttractionId(attractionService.getByUuid(attractionUuid));

        return attractionDiscussion;
    }

    @Override
    public void delete(AuthUser authUser, String attractionUuid) {
        AttractionDiscussion attractionDiscussion = getByUuid(attractionUuid);
        attractionDiscussionRepository.delete(attractionDiscussion);
    }

    @Override
    public AttractionDiscussion getByUuid(String attractionUuid) {
        return attractionDiscussionRepository.getByUuid(attractionUuid);
    }

}
