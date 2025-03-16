package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.assembler.AttractionDiscussionModelAssembler;
import com.travel.to.travel_to.entity.AuthUser;
import com.travel.to.travel_to.form.CreateAttractionDiscussionForm;
import com.travel.to.travel_to.model.AttractionDiscussionModel;
import com.travel.to.travel_to.service.AttractionDiscussionService;
import com.travel.to.travel_to.validator.CreateAttractionDiscussionFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attraction-discussion")
public class AttractionDiscussionController {

    private final CreateAttractionDiscussionFormValidator createAttractionDiscussionFormValidator;
    private final AttractionDiscussionService attractionDiscussionService;
    private final AttractionDiscussionModelAssembler attractionDiscussionModelAssembler;

    @Autowired
    public AttractionDiscussionController(
        CreateAttractionDiscussionFormValidator createAttractionDiscussionFormValidator,
        AttractionDiscussionService attractionDiscussionService,
        AttractionDiscussionModelAssembler attractionDiscussionModelAssembler
    ) {
        this.createAttractionDiscussionFormValidator = createAttractionDiscussionFormValidator;
        this.attractionDiscussionService = attractionDiscussionService;
        this.attractionDiscussionModelAssembler = attractionDiscussionModelAssembler;
    }

    @InitBinder
    public void createAttractionDiscussionFormValidator(WebDataBinder binder) {
        binder.addValidators(createAttractionDiscussionFormValidator);
    }

    @PostMapping("/create/{attractionUuid}")
    public AttractionDiscussionModel createAttractionDiscussion(
        @Validated @RequestBody CreateAttractionDiscussionForm createAttractionDiscussionForm,
        BindingResult bindingResult,
        @AuthenticationPrincipal AuthUser authUser,
        @PathVariable String attractionUuid
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return attractionDiscussionModelAssembler.toModel(
            attractionDiscussionService.create(createAttractionDiscussionForm, authUser, attractionUuid)
        );
    }

    @PostMapping("/delete/{attractionUuid}")
    public void deleteAttractionDiscussion(
        @AuthenticationPrincipal AuthUser authUser,
        @PathVariable String attractionUuid
    ) {
        attractionDiscussionService.delete(authUser, attractionUuid);
    }
}
