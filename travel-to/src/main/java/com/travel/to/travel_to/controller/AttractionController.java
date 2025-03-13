package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.assembler.AttractionModelAssembler;
import com.travel.to.travel_to.entity.Attraction;
import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.AttractionCreateForm;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.validator.AttractionCreateFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attraction")
public class AttractionController {

    private final AttractionService attractionService;
    private final AttractionCreateFormValidator attractionCreateFormValidator;
    private final AttractionModelAssembler attractionModelAssembler;

    @Autowired
    public AttractionController(
        AttractionService attractionService,
        AttractionCreateFormValidator attractionCreateFormValidator,
        AttractionModelAssembler attractionModelAssembler
    ) {
        this.attractionService = attractionService;
        this.attractionCreateFormValidator = attractionCreateFormValidator;
        this.attractionModelAssembler = attractionModelAssembler;
    }

    @InitBinder("registerBusinessBinder")
    public void attractionFormValidatorBinder(WebDataBinder binder) {
        binder.addValidators(attractionCreateFormValidator);
    }

    @GetMapping
    public Page<Attraction> getAttraction(
    ) {
        return attractionModelAssembler.toCollectionModel(attractionService.findAll());
    }

    @PostMapping("/register-business")
    public Attraction registerBusiness(
            @Validated @RequestBody AttractionCreateForm attractionCreateForm,
            BindingResult bindingResult,
            @AuthenticationPrincipal AuthUser authUser
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return attractionService.createAttraction(attractionCreateForm, authUser);
    }

}
