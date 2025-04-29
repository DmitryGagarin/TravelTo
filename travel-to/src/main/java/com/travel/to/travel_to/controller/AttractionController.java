package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.assembler.AttractionModelAssembler;
import com.travel.to.travel_to.cache.AttractionCacheUtil;
import com.travel.to.travel_to.constants.ValidationConstants;
import com.travel.to.travel_to.entity.attraction.Attraction;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu.FileMenu;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu.Menu;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu.TextMenu;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.exception.exception.FileExtensionException;
import com.travel.to.travel_to.exception.exception.TextMenuImpossibleToCreate;
import com.travel.to.travel_to.form.attraction.AttractionCreateForm;
import com.travel.to.travel_to.form.attraction.AttractionEditForm;
import com.travel.to.travel_to.form.attraction_feature.TextMenuCreateForm;
import com.travel.to.travel_to.model.AttractionModel;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.MenuService;
import com.travel.to.travel_to.validator.attraction.AttractionCreateFormValidator;
import com.travel.to.travel_to.validator.attraction.AttractionEditFormValidator;
import com.travel.to.travel_to.validator.attraction_feature.TextMenuCreateFormValidator;
import com.travel.to.travel_to.validator.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Tag(
    name = "Attraction controller",
    description = "Controller responsible for attraction actions"
)
@RestController
@RequestMapping("/attraction")
public class AttractionController {

    private final AttractionService attractionService;
    private final MenuService menuService;
    private final AttractionCreateFormValidator attractionCreateFormValidator;
    private final AttractionEditFormValidator attractionEditFormValidator;
    private final TextMenuCreateFormValidator textMenuCreateFormValidator;
    private final ValidationUtils validationUtils;
    private final AttractionModelAssembler attractionModelAssembler;
    private final AttractionCacheUtil attractionCacheUtil;

    @Autowired
    public AttractionController(
        AttractionService attractionService,
        MenuService menuService,
        AttractionCreateFormValidator attractionCreateFormValidator,
        AttractionEditFormValidator attractionEditFormValidator,
        TextMenuCreateFormValidator textMenuCreateFormValidator,
        ValidationUtils validationUtils,
        AttractionModelAssembler attractionModelAssembler,
        AttractionCacheUtil attractionCacheUtil
    ) {
        this.attractionService = attractionService;
        this.menuService = menuService;
        this.attractionCreateFormValidator = attractionCreateFormValidator;
        this.attractionEditFormValidator = attractionEditFormValidator;
        this.textMenuCreateFormValidator = textMenuCreateFormValidator;
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

    @InitBinder("textMenuCreateForm")
    public void textMenuCreateFormValidatorBinder(WebDataBinder binder) {
        binder.addValidators(textMenuCreateFormValidator);
    }

    @GetMapping("/published")
    public PagedModel<AttractionModel> getPublishedAttractions() {
        List<Attraction> attractions;

        if (Objects.isNull(attractionCacheUtil.findAll()) || attractionCacheUtil.findAll().isEmpty()) {
            attractions = attractionService.findAllByPriorityDesc();
            attractionCacheUtil.saveAll(attractions);
        } else {
            attractions = attractionCacheUtil.findAll();
        }

        List<AttractionModel> attractionModels = attractions.stream()
            .filter(attraction -> "published".equals(attraction.getStatus()))
            .map(attractionModelAssembler::toModel)
            .collect(Collectors.toList());

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

    @GetMapping("/{attractionName}/get-menu")
    public Menu getFeature (
        @PathVariable String attractionName
    ) {
        return menuService.getByMenuAttractionName(attractionName);
    }

    // TODO: add cache
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
    @PostMapping("/edit/{currentAttractionName}")
    public AttractionModel editAttraction(
        @Validated @RequestPart("attractionEditForm") AttractionEditForm attractionEditForm,
        BindingResult bindingResult,
        @RequestPart(value = "images") MultipartFile[] images,
        @PathVariable String currentAttractionName
    ) {
        return attractionModelAssembler.toModel(
            attractionService.editAttraction(attractionEditForm, images, currentAttractionName)
        );
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PostMapping("/{attractionName}/features/create-text-menu")
    public TextMenu createTextMenu(
        @Validated @RequestPart("textMenuCreateForm") TextMenuCreateForm textMenuCreateForm,
        BindingResult bindingResult,
        @RequestPart(value = "images") MultipartFile[] images,
        @PathVariable String attractionName
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new TextMenuImpossibleToCreate();
        }
        return menuService.createTextMenu(attractionName, textMenuCreateForm, images);
    }

    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    @PostMapping("/{attractionName}/features/create-file-menu")
    public FileMenu createFileMenu(
        @RequestPart(value = "files") MultipartFile[] files,
        @PathVariable String attractionName
    ) throws IOException {
        // TODO: проверка на формат файлов
        return menuService.createFileMenu(attractionName, files);
    }

}
