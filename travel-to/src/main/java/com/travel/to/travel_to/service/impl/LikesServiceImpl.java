package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.Likes;
import com.travel.to.travel_to.repository.LikesRepository;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.LikesService;
import com.travel.to.travel_to.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final AttractionService attractionService;
    private final UserService userService;

    @Autowired
    public LikesServiceImpl (
        LikesRepository likesRepository,
        AttractionService attractionService,
        UserService userService
    ) {
        this.likesRepository = likesRepository;
        this.attractionService = attractionService;
        this.userService = userService;
    }

    @Override
    @NotNull
    public Likes addLike(
        @NotNull String attractionName,
        @NotNull AuthUser authUser
    ) {
        Likes like = new Likes();
        like
            .setCreatedAt(LocalDateTime.now())
            .setUpdatedAt(LocalDateTime.now());
        like
            .setAttractionId(attractionService.getByName(attractionName).getId())
            .setUserId(userService.getByUuid(authUser.getUuid()).getId());
        return likesRepository.save(like);
    }

    @Override
    @NotNull
    public List<Likes> getAllByUser(@NotNull AuthUser authUser) {
        Long id = userService.getByUuid(authUser.getUuid()).getId();
        return likesRepository.findAllByUserId(id);
    }
}
