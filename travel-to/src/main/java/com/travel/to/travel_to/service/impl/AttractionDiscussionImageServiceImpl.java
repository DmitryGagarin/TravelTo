package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.attraction.AttractionDiscussionImage;
import com.travel.to.travel_to.repository.AttractionDiscussionImageRepository;
import com.travel.to.travel_to.service.AttractionDiscussionImageService;
import com.travel.to.travel_to.utils.ImageUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttractionDiscussionImageServiceImpl implements AttractionDiscussionImageService {

    private final AttractionDiscussionImageRepository attractionDiscussionImageRepository;

    @Autowired
    public AttractionDiscussionImageServiceImpl(
        AttractionDiscussionImageRepository attractionDiscussionImageRepository
    ) {
        this.attractionDiscussionImageRepository = attractionDiscussionImageRepository;
    }

    @Override
    @NotNull
    public List<AttractionDiscussionImage> create(
        @NotNull Long discussionId,
        @NotNull MultipartFile[] images
    ) throws IOException {
        List<AttractionDiscussionImage> attractionDiscussionImages = new ArrayList<>();
        for (MultipartFile image : images) {
            attractionDiscussionImages.add(
                new AttractionDiscussionImage()
                    .setAttractionDiscussionId(discussionId)
                    .setImage(image.getBytes())
                    .setImageFormat(ImageUtils.getImageFormat(image))
            );
        }
        return attractionDiscussionImageRepository.saveAll(attractionDiscussionImages);
    }

    @Override
    @NotNull
    public List<byte[]> getAllImagesByDiscussionId(
        @NotNull Long discussionId
    ) {
        return attractionDiscussionImageRepository.getAllImagesByDiscussionId(discussionId);
    }

    @Override
    public @NotNull List<String> getAllImageFormatByDiscussionId(@NotNull Long discussionId) {
        return attractionDiscussionImageRepository.getAllImageFormatByAttractionDiscussionId(discussionId);
    }

}
