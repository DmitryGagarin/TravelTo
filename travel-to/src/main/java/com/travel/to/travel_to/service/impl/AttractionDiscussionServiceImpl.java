package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.attraction.AttractionDiscussion;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.CreateAttractionDiscussionForm;
import com.travel.to.travel_to.repository.AttractionDiscussionRepository;
import com.travel.to.travel_to.service.AttractionDiscussionImageService;
import com.travel.to.travel_to.service.AttractionDiscussionService;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttractionDiscussionServiceImpl implements AttractionDiscussionService {

    private final UserService userService;
    private final AttractionService attractionService;
    private final AttractionDiscussionImageService attractionDiscussionImageService;
    private final AttractionDiscussionRepository attractionDiscussionRepository;

    @Autowired
    public AttractionDiscussionServiceImpl(
        UserService userService,
        AttractionService attractionService,
        AttractionDiscussionImageService attractionDiscussionImageService,
        AttractionDiscussionRepository attractionDiscussionRepository
    ) {
        this.userService = userService;
        this.attractionService = attractionService;
        this.attractionDiscussionImageService = attractionDiscussionImageService;
        this.attractionDiscussionRepository = attractionDiscussionRepository;
    }

    @Override
    public AttractionDiscussion create(
        @NotNull CreateAttractionDiscussionForm createAttractionDiscussionForm,
        @NotNull AuthUser authUser,
        @NotNull String attractionUuid,
        @NotNull MultipartFile[] images
    ) throws IOException {
        Long attractionId = attractionService.getByUuid(attractionUuid).getId();

        AttractionDiscussion attractionDiscussion = new AttractionDiscussion();
        attractionDiscussion
            .setTitle(createAttractionDiscussionForm.getTitle())
            .setContentLike(createAttractionDiscussionForm.getContentLike())
            .setContentDislike(createAttractionDiscussionForm.getContentDislike())
            .setContent(createAttractionDiscussionForm.getContent())
            .setRating(createAttractionDiscussionForm.getRating())
            .setAuthorId(userService.findByUuid(authUser.getUuid()))
            .setAttractionId(attractionId)
            .setCreatedAt(LocalDateTime.now())
            .setUpdatedAt(LocalDateTime.now());
        attractionDiscussionRepository.save(attractionDiscussion);

        attractionDiscussionImageService.create(attractionDiscussion.getId(), images);

        // TODO: подсчет рейтинга не совсем правильно работает
        List<Double> ratings = attractionService.findAllAttractionRatingByAttractionId(attractionId);
        ratings.add(attractionDiscussion.getRating());
        double totalRating = ratings.stream().mapToDouble(i -> i).sum();
        attractionService.updateRating(attractionUuid, totalRating / attractionDiscussionRepository.findAll().size());

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

    @Override
    public List<AttractionDiscussion> findAllByAttractionUuid(String attractionUuid) {
        return attractionDiscussionRepository.findAllByAttractionId(
            attractionService.getByUuid(attractionUuid).getId()
        );
    }

}
