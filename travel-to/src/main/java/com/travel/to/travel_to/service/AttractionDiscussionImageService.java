package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.attraction.AttractionDiscussionImage;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AttractionDiscussionImageService {

    @NotNull
    List<AttractionDiscussionImage> create(
        @NotNull Long discussionId,
        @NotNull MultipartFile[] images
    ) throws IOException;

    @NotNull
    List<byte[]> getAllImagesByDiscussionId(
        @NotNull Long discussionId
    );

    @NotNull
    List<String> getAllImageFormatByDiscussionId(
        @NotNull Long discussionId
    );

}
