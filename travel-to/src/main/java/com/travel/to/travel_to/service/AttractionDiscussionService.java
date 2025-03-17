package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.AttractionDiscussion;
import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.CreateAttractionDiscussionForm;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface AttractionDiscussionService {

    AttractionDiscussion create(
        CreateAttractionDiscussionForm createAttractionDiscussionForm,
        AuthUser authuser,
        String attractionUuid
    );

    void delete(
        AuthUser authUser,
        String attractionUuid
    );

    @NotNull
    AttractionDiscussion getByUuid(String attractionUuid);

    @NotNull
    List<AttractionDiscussion> findAllByAttractionUuid(String attractionUuid);

}
