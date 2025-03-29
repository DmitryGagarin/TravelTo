package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.assembler.AttractionDiscussionModelAssembler;
import com.travel.to.travel_to.entity.attraction.AttractionDiscussion;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.AttractionDiscussionCreateForm;
import com.travel.to.travel_to.model.AttractionDiscussionModel;
import com.travel.to.travel_to.service.AttractionDiscussionService;
import com.travel.to.travel_to.validator.attraction_discussion.AttractionDiscussionCreateFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attraction-discussion")
public class AttractionDiscussionController {

    private final AttractionDiscussionCreateFormValidator attractionDiscussionCreateFormValidator;
    private final AttractionDiscussionService attractionDiscussionService;
    private final AttractionDiscussionModelAssembler attractionDiscussionModelAssembler;

    @Autowired
    public AttractionDiscussionController(
        AttractionDiscussionCreateFormValidator attractionDiscussionCreateFormValidator,
        AttractionDiscussionService attractionDiscussionService,
        AttractionDiscussionModelAssembler attractionDiscussionModelAssembler
    ) {
        this.attractionDiscussionCreateFormValidator = attractionDiscussionCreateFormValidator;
        this.attractionDiscussionService = attractionDiscussionService;
        this.attractionDiscussionModelAssembler = attractionDiscussionModelAssembler;
    }

    @InitBinder
    public void createAttractionDiscussionFormValidator(WebDataBinder binder) {
        binder.addValidators(attractionDiscussionCreateFormValidator);
    }

    @GetMapping("/{attractionUuid}")
    public PagedModel<AttractionDiscussionModel> list(
        @PathVariable String attractionUuid
    ) {
        List<AttractionDiscussion> attractionDiscussions = attractionDiscussionService.findAllByAttractionUuid(attractionUuid);
        List<AttractionDiscussionModel> attractionDiscussionModels = attractionDiscussions.stream()
            .map(attractionDiscussionModelAssembler::toModel)
            .collect(Collectors.toList());

        int pageSize = attractionDiscussionModels.size();
        int currentPage = 0;
        int totalElements = attractionDiscussionModels.size();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(pageSize, currentPage, totalElements, totalPages);
        return PagedModel.of(attractionDiscussionModels, pageMetadata);
    }

    @PostMapping("/create/{attractionUuid}")
    public AttractionDiscussionModel createAttractionDiscussion(
        @Validated @RequestPart("attractionDiscussionCreateForm")
        AttractionDiscussionCreateForm attractionDiscussionCreateForm,
        BindingResult bindingResult,
        @RequestPart(value = "images", required = false)
        MultipartFile[] images,
        @AuthenticationPrincipal AuthUser authUser,
        @PathVariable String attractionUuid
    ) throws BindException, IOException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return attractionDiscussionModelAssembler.toModel(
            attractionDiscussionService.create(attractionDiscussionCreateForm, authUser, attractionUuid, images)
        );
    }

    @PreAuthorize("hasAnyAuthority('ROLE_DISCUSSION_OWNER', 'ROLE_ADMIN')")
    @PostMapping("/delete/{attractionUuid}")
    public void deleteAttractionDiscussion(
        @PathVariable String attractionUuid,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        attractionDiscussionService.delete(authUser, attractionUuid);
    }


}
