package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.AttractionImage;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AttractionImageService {
    @NotNull
    List<AttractionImage> create(
        @NotNull MultipartFile[] images,
        @NotNull Long attractionId
    ) throws IOException;

    @NotNull
    List<byte[]> getAllImagesByAttractionId(
        @NotNull Long attractionId
    );
}
