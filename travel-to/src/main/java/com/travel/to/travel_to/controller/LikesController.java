package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.assembler.LikesModelAssembler;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.Likes;
import com.travel.to.travel_to.model.LikesModel;
import com.travel.to.travel_to.service.LikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/like")
public class LikesController {

    private final LikesService likesService;
    private final LikesModelAssembler likesModelAssembler;

    @Autowired
    public LikesController(
        LikesService likesService,
        LikesModelAssembler likesModelAssembler
    ) {
        this.likesService = likesService;
        this.likesModelAssembler = likesModelAssembler;
    }

    @GetMapping
    public PagedModel<LikesModel> list(
        @AuthenticationPrincipal AuthUser authUser
    ) {
        List<Likes> likes = likesService.getAllByUser(authUser);
        List<LikesModel> likesModels = likes.stream()
            .map(likesModelAssembler::toModel)
            .collect(Collectors.toList());

        int pageSize = likesModels.size();
        int currentPage = 0;
        int totalElements = likesModels.size();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(pageSize, currentPage, totalElements, totalPages);
        return PagedModel.of(likesModels, pageMetadata);
    }

    @PostMapping("/add/{attractionName}")
    public LikesModel like(
        @PathVariable String attractionName,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        return likesModelAssembler.toModel(likesService.addLike(attractionName, authUser));
    }

}
