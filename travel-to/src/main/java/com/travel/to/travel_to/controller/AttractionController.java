package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.assembler.AttractionModelAssembler;
import com.travel.to.travel_to.constants.ValidationConstants;
import com.travel.to.travel_to.entity.attraction.Attraction;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.exception.exception.FileExtensionException;
import com.travel.to.travel_to.form.AttractionCreateForm;
import com.travel.to.travel_to.form.AttractionEditForm;
import com.travel.to.travel_to.model.AttractionModel;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.cache.AttractionCacheUtil;
import com.travel.to.travel_to.validator.attraction.AttractionCreateFormValidator;
import com.travel.to.travel_to.validator.attraction.AttractionEditFormValidator;
import com.travel.to.travel_to.validator.utils.ValidationUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attraction")
public class AttractionController {

    private final AttractionService attractionService;
    private final AttractionCreateFormValidator attractionCreateFormValidator;
    private final AttractionEditFormValidator attractionEditFormValidator;
    private final ValidationUtils validationUtils;
    private final AttractionModelAssembler attractionModelAssembler;
    private final AttractionCacheUtil attractionCacheUtil;

    @Autowired
    public AttractionController(
        AttractionService attractionService,
        AttractionCreateFormValidator attractionCreateFormValidator,
        AttractionEditFormValidator attractionEditFormValidator,
        ValidationUtils validationUtils,
        AttractionModelAssembler attractionModelAssembler,
        AttractionCacheUtil attractionCacheUtil) {
        this.attractionService = attractionService;
        this.attractionCreateFormValidator = attractionCreateFormValidator;
        this.attractionEditFormValidator = attractionEditFormValidator;
        this.validationUtils = validationUtils;
        this.attractionModelAssembler = attractionModelAssembler;
        this.attractionCacheUtil = attractionCacheUtil;
    }

    @InitBinder("attractionCreateForm")
    public void attractionCreateFormValidatorBinder(WebDataBinder binder) {
        binder.addValidators(attractionCreateFormValidator);
    }

    @InitBinder("attractionEditForm")
    public void attractionEditFormValidatorBinder(WebDataBinder binder) {
        binder.addValidators(attractionEditFormValidator);
    }

    @GetMapping
    public PagedModel<AttractionModel> getAttractions() {
        List<Attraction> attractions;

        if (Objects.isNull(attractionCacheUtil.findAll()) || attractionCacheUtil.findAll().isEmpty()) {
            attractions = attractionService.findAll();
            attractionCacheUtil.saveAll(attractions);
        } else {
            attractions = attractionCacheUtil.findAll();
        }

        List<AttractionModel> attractionModels = new ArrayList<>();
        for (Attraction attraction : attractions) {
            AttractionModel attractionModel = attractionModelAssembler.toModel(attraction);
            attractionModels.add(attractionModel);
        }

        int pageSize = attractionModels.size();
        int currentPage = 0;
        int totalElements = attractionModels.size();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(pageSize, currentPage, totalElements, totalPages);

        return PagedModel.of(attractionModels, pageMetadata);
    }

    @GetMapping("/{name}")
    public AttractionModel getAttractionByName(
        @PathVariable String name
    ) {
        return attractionModelAssembler.toModel(attractionService.getByName(name));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_ADMIN')")
    @GetMapping("/my")
    public PagedModel<AttractionModel> getAttractionsByOwner(
        @AuthenticationPrincipal AuthUser authUser
    ) {
        List<Attraction> attractions = attractionService.findAllByOwner(authUser);
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

    @PostMapping("/register-business")
    public AttractionModel registerBusiness(
        @Validated @RequestPart("attractionCreateForm") AttractionCreateForm attractionCreateForm,
        BindingResult bindingResult,
        @RequestPart(value = "images") MultipartFile[] images,
        @AuthenticationPrincipal AuthUser authUser
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        if (images.length > ValidationConstants.MAX_IMAGE_NUMBER) {
            throw new FileExtensionException("Only 10 images are allowed");
        }
        if (!validationUtils.validateImageFileFormat(images, bindingResult)) {
            throw new FileExtensionException("This image format not allowed");
        }
        return attractionModelAssembler.toModel(
            attractionService.createAttraction(attractionCreateForm, authUser, images)
        );
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_ADMIN')")
    @PostMapping("/delete/{name}")
    public void deleteAttraction(
        @PathVariable String name,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        attractionService.deleteAttractionByName(name, authUser);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PostMapping("/edit/{currentName}")
    public AttractionModel editAttraction(
        @Validated @RequestPart("attractionEditForm") AttractionEditForm attractionEditForm,
        BindingResult bindingResult,
        @RequestPart(value = "images") MultipartFile[] images,
        @PathVariable String currentName
    ) {
        return attractionModelAssembler.toModel(
            attractionService.editAttraction(attractionEditForm, images, currentName)
        );
    }

}
