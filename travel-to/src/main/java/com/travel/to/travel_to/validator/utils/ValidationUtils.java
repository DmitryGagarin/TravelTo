package com.travel.to.travel_to.validator.utils;

import com.travel.to.travel_to.constants.ValidationConstants;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
public class ValidationUtils {

    public boolean validateImageFileFormat(
        @NotNull MultipartFile[] images,
        @NotNull BindingResult bindingResult
    ) {
        boolean isValid = true;
        for (MultipartFile image : images) {
            String imageExtension =
                Objects.requireNonNull(image.getOriginalFilename())
                    .substring(1 + image.getOriginalFilename().lastIndexOf("."));
            if (!
                (!image.isEmpty()
                && ValidationConstants.ALLOWED_IMAGE_FORMATS.contains(imageExtension)
                && image.getSize() <= ValidationConstants.MAX_FILE_SIZE)
            ) {
                isValid = false;
                break;
            }

        }
        return isValid;
    }
}
