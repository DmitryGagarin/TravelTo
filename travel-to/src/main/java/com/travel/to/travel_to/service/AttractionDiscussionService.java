package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.attraction.AttractionDiscussion;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.CreateAttractionDiscussionForm;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AttractionDiscussionService {

    @NotNull
    AttractionDiscussion create(
        @NotNull CreateAttractionDiscussionForm createAttractionDiscussionForm,
        @NotNull AuthUser authuser,
        @NotNull String attractionUuid,
        @NotNull MultipartFile[] images
    ) throws IOException;

    void delete(
        AuthUser authUser,
        String attractionUuid
    );

    @NotNull
    AttractionDiscussion getByUuid(String attractionUuid);

    @NotNull
    List<AttractionDiscussion> findAllByAttractionUuid(String attractionUuid);

}
