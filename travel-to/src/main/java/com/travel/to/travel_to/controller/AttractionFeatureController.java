package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.assembler.FacilityModelAssembler;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu.FileMenu;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu.TextMenu;
import com.travel.to.travel_to.exception.exception.TextMenuImpossibleToCreate;
import com.travel.to.travel_to.form.attraction_feature.ParkFacilityCreateForm;
import com.travel.to.travel_to.form.attraction_feature.TextMenuCreateForm;
import com.travel.to.travel_to.model.FacilityModel;
import com.travel.to.travel_to.model.TheaterPosterModel;
import com.travel.to.travel_to.assembler.PosterModelAssembler;
import com.travel.to.travel_to.service.MenuService;
import com.travel.to.travel_to.service.ParkFacilityService;
import com.travel.to.travel_to.service.PosterService;
import com.travel.to.travel_to.validator.attraction_feature.ParkFacilityCreateFormValidator;
import com.travel.to.travel_to.validator.attraction_feature.TextMenuCreateFormValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@Tag(
    name = "Attraction feature controller",
    description = "For now returns: TextMenu, FileMenu, Facility (for now for parks only)"
)
@RestController
@RequestMapping("/attraction-feature/{attractionName}")
public class AttractionFeatureController {

    private final MenuService menuService;
    private final ParkFacilityService parkFacilityService;
    private final PosterService posterService;
    private final TextMenuCreateFormValidator textMenuCreateFormValidator;
    private final ParkFacilityCreateFormValidator parkFacilityCreateFormValidator;
    private final FacilityModelAssembler facilityModelAssembler;
    private final PosterModelAssembler posterModelAssembler;

    @Autowired
    public AttractionFeatureController(
        MenuService menuService,
        ParkFacilityService parkFacilityService,
        PosterService posterService,
        TextMenuCreateFormValidator textMenuCreateFormValidator,
        ParkFacilityCreateFormValidator parkFacilityCreateFormValidator,
        FacilityModelAssembler facilityModelAssembler,
        PosterModelAssembler posterModelAssembler
    ) {
        this.menuService = menuService;
        this.parkFacilityService = parkFacilityService;
        this.posterService = posterService;
        this.textMenuCreateFormValidator = textMenuCreateFormValidator;
        this.parkFacilityCreateFormValidator = parkFacilityCreateFormValidator;
        this.facilityModelAssembler = facilityModelAssembler;
        this.posterModelAssembler = posterModelAssembler;
    }

    @InitBinder("textMenuCreateForm")
    public void textMenuCreateFormValidatorBinder(WebDataBinder binder) {
        binder.addValidators(textMenuCreateFormValidator);
    }

    @InitBinder("parkFacilityCreateForm")
    public void parkFacilityCreateFormValidatorBinder(WebDataBinder binder) {
        binder.addValidators(parkFacilityCreateFormValidator);
    }

    @GetMapping("/get-text-menu")
    public Optional<TextMenu> getTextMenuFeature (
        @PathVariable String attractionName
    ) {
        return menuService.getTextMenuByAttractionName(attractionName);
    }

    @GetMapping("/get-file-menu")
    public Optional<FileMenu> getFileMenuFeature (
        @PathVariable String attractionName
    ) {
        return menuService.getFileMenuByAttractionName(attractionName);
    }

    @GetMapping("/get-park-facility")
    public Optional<List<FacilityModel>> getParkFacilityFeature (
        @PathVariable String attractionName
    ) {
        return facilityModelAssembler.toOptionalModels(
            parkFacilityService.getFacilityByAttractionName(attractionName)
        );
    }

    @PostMapping("/create-text-menu")
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

    @PostMapping("/create-file-menu")
    public FileMenu createFileMenu(
        @RequestPart(value = "files") MultipartFile[] files,
        @PathVariable String attractionName
    ) throws IOException {
        // TODO: проверка на формат файлов
        return menuService.createFileMenu(attractionName, files);
    }

    @PostMapping("/create-park-facility")
    public List<FacilityModel> createParkFacility(
        @RequestPart(value = "images") MultipartFile[] images,
        @PathVariable String attractionName,
        @Validated @RequestPart("parkFacilityCreateForm")
        ParkFacilityCreateForm parkFacilityCreateForm,
        BindingResult bindingResult
    ) throws BindException, IOException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return facilityModelAssembler.toModels(
            parkFacilityService.createFacility(images, attractionName, parkFacilityCreateForm)
        );
    }

    @PostMapping("/create-theatre-poster")
    public List<TheaterPosterModel> createTheatrePoster(
        @RequestPart(value = "images") MultipartFile[] images,
        @PathVariable String attractionName
    ) throws IOException {
        return posterModelAssembler.toModels(
            posterService.createTheaterPoster(images, attractionName)
        );
    }

}
