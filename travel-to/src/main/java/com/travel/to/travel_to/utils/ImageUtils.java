package com.travel.to.travel_to.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class ImageUtils {
    public static String getImageFormat(MultipartFile image) {
        return (
            Objects.requireNonNull(image.getOriginalFilename())
                .substring(1 + image.getOriginalFilename().lastIndexOf("."))
        );
    }
}
