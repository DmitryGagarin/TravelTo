package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.attraction.AttractionImage;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AttractionImageService {

    @NotNull
    List<AttractionImage> save(
        @NotNull MultipartFile[] images,
        @NotNull Long attractionId
    ) throws IOException;

    @NotNull
    List<AttractionImage> edit(
        @NotNull MultipartFile[] images,
        @NotNull Long attractionId
    ) throws IOException;

    @NotNull
    List<byte[]> getAllImagesByAttractionId(
        @NotNull Long attractionId
    );
}
