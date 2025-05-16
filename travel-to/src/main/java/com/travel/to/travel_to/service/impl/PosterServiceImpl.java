package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.attraction_feature.poster.Poster;
import com.travel.to.travel_to.repository.PosterRepository;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.PosterService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.travel.to.travel_to.utils.ImageUtils.getImageFormat;

@Service
public class PosterServiceImpl implements PosterService {

    private final AttractionService attractionService;
    private final PosterRepository posterRepository;

    @Autowired
    public PosterServiceImpl(
        AttractionService attractionService,
        PosterRepository posterRepository
    ) {
        this.attractionService = attractionService;
        this.posterRepository = posterRepository;
    }

    @Override
    @NotNull
    public List<Poster> createPoster(
        @NotNull MultipartFile[] images,
        @NotNull String attractionName
    ) throws IOException {
        List<Poster> posters = new ArrayList<>();
        for (MultipartFile image : images) {
            Poster poster = new Poster();
            poster
                .setImage(image.getBytes())
                .setAttractionId(attractionService.getByName(attractionName).getId())
                .setImageFormat(getImageFormat(image));
            posters.add(poster);
        }
        return posterRepository.saveAll(posters);
    }

    @Override
    public Optional<List<Poster>> getPostersByAttractionName(@NotNull String attractionName) {
       return posterRepository.findAllByAttractionId(attractionService.getByName(attractionName).getId());
    }

    @Override
    public void deleteByAttractionId(@NotNull Long attractionId) {
        posterRepository.deleteByAttractionId(attractionId);
    }
}
