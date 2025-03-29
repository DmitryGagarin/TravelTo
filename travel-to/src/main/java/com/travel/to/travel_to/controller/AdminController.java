package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.assembler.AttractionModelAssembler;
import com.travel.to.travel_to.entity.attraction.Attraction;
import com.travel.to.travel_to.entity.attraction.AttractionStatus;
import com.travel.to.travel_to.model.AttractionModel;
import com.travel.to.travel_to.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AttractionModelAssembler attractionModelAssembler;
    private final AttractionService attractionService;

    @Autowired
    public AdminController(
        AttractionModelAssembler attractionModelAssembler,
        AttractionService attractionService
    ) {
        this.attractionModelAssembler = attractionModelAssembler;
        this.attractionService = attractionService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/moderation/{type}")
    public PagedModel<AttractionModel> getAttractionsOnModeration(
        @PathVariable String type
    ) {
        List<Attraction> attractions = attractionService.findAllByStatus(type.toLowerCase());
        List<AttractionModel> attractionModels = attractions.stream()
            .map(attractionModelAssembler::toModel)
            .collect(Collectors.toList());

        int pageSize = attractionModels.size();
        int currentPage = 0;
        int totalElements = attractionModels.size();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(pageSize, currentPage, totalElements, totalPages);

        return PagedModel.of(attractionModels, pageMetadata);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/apply-moderation/{name}")
    public AttractionModel getAttractionsApplyModeration(
        @PathVariable String name
    ) {
        return attractionModelAssembler.toModel(
            attractionService.updateAttractionStatus(AttractionStatus.published, name)
        );
    }
}