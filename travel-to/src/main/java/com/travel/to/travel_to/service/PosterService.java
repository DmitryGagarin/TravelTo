package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.attraction_feature.poster.Poster;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PosterService {

    @NotNull
    List<Poster> createPoster(
        @NotNull MultipartFile[] images,
        @NotNull String attractionName
    ) throws IOException;
}
