package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.attraction_feature.poster.TheaterPoster;
import com.travel.to.travel_to.repository.TheaterPosterRepository;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.PosterService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.travel.to.travel_to.utils.ImageUtils.getImageFormat;

@Service
public class PosterServiceImpl implements PosterService {

    private final AttractionService attractionService;
    private final TheaterPosterRepository theaterPosterRepository;

    @Autowired
    public PosterServiceImpl(
        AttractionService attractionService,
        TheaterPosterRepository theaterPosterRepository
    ) {
        this.attractionService = attractionService;
        this.theaterPosterRepository = theaterPosterRepository;
    }

    @Override
    @NotNull
    public List<TheaterPoster> createTheaterPoster(
        @NotNull MultipartFile[] images,
        @NotNull String attractionName
    ) throws IOException {
        List<TheaterPoster> theaterPosters = new ArrayList<>();
        for (MultipartFile image : images) {
            TheaterPoster theaterPoster = new TheaterPoster();
            theaterPoster
                .setImage(image.getBytes())
                .setAttractionId(attractionService.getByName(attractionName).getId())
                .setImageFormat(getImageFormat(image));
            theaterPosters.add(theaterPoster);
        }
        return theaterPosterRepository.saveAll(theaterPosters);
    }
}
