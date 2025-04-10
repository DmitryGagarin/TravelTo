package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.attraction.AttractionDiscussion;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.AttractionDiscussionCreateForm;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AttractionDiscussionService {

    @NotNull
    AttractionDiscussion create(
        @NotNull AttractionDiscussionCreateForm attractionDiscussionCreateForm,
        @NotNull AuthUser authuser,
        @NotNull String attractionUuid,
        @NotNull MultipartFile[] images
    ) throws IOException;

    @NotNull
    AttractionDiscussion getByUuid(String attractionUuid);

    @NotNull
    List<AttractionDiscussion> findAllByAttractionUuid(String attractionUuid);

    void delete(
        AuthUser authUser,
        String attractionUuid
    );
}
