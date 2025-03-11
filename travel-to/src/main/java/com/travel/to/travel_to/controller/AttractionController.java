package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attraction")
public class AttractionController {

    private final AttractionService attractionService;

    @Autowired
    public AttractionController(
        AttractionService attractionService
    ) {
        this.attractionService = attractionService;
    }

    @GetMapping
    public Page<Attraction> getAttraction(
        Pageable pageable
    ) {
        return attractionService.findAll(pageable);
    }

}
