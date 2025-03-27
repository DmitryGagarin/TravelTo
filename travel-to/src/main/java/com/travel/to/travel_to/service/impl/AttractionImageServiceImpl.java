package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.attraction.AttractionImage;
import com.travel.to.travel_to.repository.AttractionImageRepository;
import com.travel.to.travel_to.service.AttractionImageService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttractionImageServiceImpl implements AttractionImageService {

    private final AttractionImageRepository attractionImageRepository;

    @Autowired
    public AttractionImageServiceImpl(
        AttractionImageRepository attractionImageRepository
    ) {
        this.attractionImageRepository = attractionImageRepository;
    }

    @Override
    public List<AttractionImage> save(
        @NotNull MultipartFile[] images,
        @NotNull Long attractionId
    ) throws IOException {
        List<AttractionImage> attractionImages = new ArrayList<>();
        for (MultipartFile image : images) {
            attractionImages.add(
                new AttractionImage()
                    .setAttractionId(attractionId)
                    .setImage(image.getBytes())
            );
        }
        return attractionImageRepository.saveAll(attractionImages);
    }

    @Override
    @NotNull
    public List<byte[]> getAllImagesByAttractionId(Long attractionId) {
        return attractionImageRepository.getAllImagesByAttractionId(attractionId);
    }

}
