package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.entity.attraction_feature.menu.menu.FileMenu;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu.TextMenu;
import com.travel.to.travel_to.exception.exception.TextMenuImpossibleToCreate;
import com.travel.to.travel_to.form.attraction_feature.TextMenuCreateForm;
import com.travel.to.travel_to.service.MenuService;
import com.travel.to.travel_to.validator.attraction_feature.TextMenuCreateFormValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@Tag(
    name = "Attraction feature controller",
    description = "For now returns: TextMenu, FileMenu"
)
@RestController
@RequestMapping("/attraction-feature")
public class AttractionFeatureController {

    private final MenuService menuService;
    private final TextMenuCreateFormValidator textMenuCreateFormValidator;

    @Autowired
    public AttractionFeatureController(
        MenuService menuService,
        TextMenuCreateFormValidator textMenuCreateFormValidator
    ) {
        this.menuService = menuService;
        this.textMenuCreateFormValidator = textMenuCreateFormValidator;
    }

    @InitBinder("textMenuCreateForm")
    public void textMenuCreateFormValidatorBinder(WebDataBinder binder) {
        binder.addValidators(textMenuCreateFormValidator);
    }


    @GetMapping("/{attractionName}/get-text-menu")
    public Optional<TextMenu> getTextMenuFeature (
        @PathVariable String attractionName
    ) {
        return menuService.getTextMenuByAttractionName(attractionName);
    }

    @GetMapping("/{attractionName}/get-file-menu")
    public Optional<FileMenu> getFileMenuFeature (
        @PathVariable String attractionName
    ) {
        return menuService.getFileMenuByAttractionName(attractionName);
    }

    @PostMapping("/{attractionName}/create-text-menu")
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

    @PostMapping("/{attractionName}/create-file-menu")
    public FileMenu createFileMenu(
        @RequestPart(value = "files") MultipartFile[] files,
        @PathVariable String attractionName
    ) throws IOException {
        // TODO: проверка на формат файлов
        return menuService.createFileMenu(attractionName, files);
    }
}
