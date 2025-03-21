package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.Likes;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface LikesService {

    @NotNull
    Likes addLike(
        @NotNull String attractionName,
        @NotNull AuthUser authUser
    );

    @NotNull
    List<Likes> getAllByUser(
        @NotNull AuthUser authUser
    );

}
