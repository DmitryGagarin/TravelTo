package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.attraction_feature.poster.TheaterPoster;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PosterService {

    @NotNull
    List<TheaterPoster> createTheaterPoster(
        @NotNull MultipartFile[] images,
        @NotNull String attractionName
    ) throws IOException;
}
