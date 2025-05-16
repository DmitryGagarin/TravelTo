package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.assembler.AttractionModelAssembler;
import com.travel.to.travel_to.assembler.LikesModelAssembler;
import com.travel.to.travel_to.entity.Likes;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.model.AttractionModel;
import com.travel.to.travel_to.model.LikesModel;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.LikesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Tag(
    name = "Likes controller",
    description = "Controller is responsible for likes actions"
)
@RestController
@RequestMapping("/like")
public class LikesController {

    private final LikesService likesService;
    private final LikesModelAssembler likesModelAssembler;
    private final AttractionModelAssembler attractionModelAssembler;
    private final AttractionService attractionService;

    @Autowired
    public LikesController(
        LikesService likesService,
        LikesModelAssembler likesModelAssembler,
        AttractionModelAssembler attractionModelAssembler, AttractionService attractionService) {
        this.likesService = likesService;
        this.likesModelAssembler = likesModelAssembler;
        this.attractionModelAssembler = attractionModelAssembler;
        this.attractionService = attractionService;
    }

    // finds likes of user by current user id and returns attractions which are liked by him
    @GetMapping
    public PagedModel<List<AttractionModel>> list(
        @AuthenticationPrincipal AuthUser authUser
    ) {
        List<Likes> likes = likesService.getAllByUser(authUser);
        List<AttractionModel> likedAttractionModels = new ArrayList<>();
        for (Likes like : likes) {
            AttractionModel attractionModel =
                attractionModelAssembler.toModel(
                    attractionService.getById(like.getAttractionId())
                );
            likedAttractionModels.add(attractionModel);
        }

        int pageSize = likedAttractionModels.size();
        int currentPage = 0;
        int totalElements = likedAttractionModels.size();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(pageSize, currentPage, totalElements, totalPages);
        return PagedModel.of(Collections.singleton(likedAttractionModels), pageMetadata);
    }

    @GetMapping("/is-liked/{attractionName}")
    public boolean isAttractionLiked(
        @PathVariable String attractionName,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        return likesService.isLikedByUser(attractionName, authUser);
    }

    @PostMapping("/add/{attractionName}")
    public LikesModel like(
        @PathVariable String attractionName,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        return likesModelAssembler.toModel(likesService.addLike(attractionName, authUser));
    }

    @PostMapping("/delete/{attractionName}")
    public void delete(
        @PathVariable String attractionName,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        likesService.deleteLike(attractionName, authUser);
    }

}
