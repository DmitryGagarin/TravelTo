package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.attraction_feature.menu.menu.FileMenu;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu.Menu;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu.TextMenu;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu_element.FileMenuElement;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu_element.TextMenuElement;
import com.travel.to.travel_to.form.attraction_feature.TextMenuCreateForm;
import com.travel.to.travel_to.repository.FileMenuRepository;
import com.travel.to.travel_to.repository.TextMenuRepository;
import com.travel.to.travel_to.service.AttractionService;
import com.travel.to.travel_to.service.MenuService;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MenuServiceImpl implements MenuService {

    private final AttractionService attractionService;
    private final TextMenuRepository textMenuRepository;
    private final FileMenuRepository fileMenuRepository;

    public MenuServiceImpl(
        AttractionService attractionService,
        TextMenuRepository textMenuRepository,
        FileMenuRepository fileMenuRepository
    ) {
        this.attractionService = attractionService;
        this.textMenuRepository = textMenuRepository;
        this.fileMenuRepository = fileMenuRepository;
    }

    @Override
    @NotNull
    @Transactional
    public TextMenu createTextMenu(
        @NotNull String attractionName,
        @NotNull TextMenuCreateForm form,
        @NotNull MultipartFile[] images
    ) throws IOException {
        Long attractionId = attractionService.getByName(attractionName).getId();

        TextMenu textMenu = new TextMenu();
        List<TextMenuElement> textMenuElements = new ArrayList<>();

        for (int i = 0; i < form.getNames().size(); i++) {
            TextMenuElement element = new TextMenuElement();
            element.setAttractionId(attractionId);
            element
                .setDishName(form.getNames().get(i))
                .setDishDescription(form.getDescriptions().get(i))
                .setDishPrice(form.getPrices().get(i))
                .setDishImage(images[i].getBytes())
                .setMenu(textMenu);
            textMenuElements.add(element);
        }

        textMenu
            .setElements(textMenuElements)
            .setAttractionId(attractionId);
        return textMenuRepository.save(textMenu);
    }

    @Override
    public TextMenu findTextMenuByAttractionId(@NotNull Long attractionId) {
        return textMenuRepository.findByAttractionId(attractionId);
    }

    @Override
    @NotNull
    @Transactional
    public FileMenu createFileMenu(
        @NotNull String attractionName,
        @NotNull MultipartFile[] files
    ) throws IOException {
        List<FileMenuElement> fileMenuElements = new ArrayList<>();

        FileMenu fileMenu = new FileMenu();
        Long attractionId = attractionService.getByName(attractionName).getId();

        for (MultipartFile file : files) {
            FileMenuElement fileMenuElement = new FileMenuElement();
            fileMenuElement.setAttractionId(attractionId);
            fileMenuElement
                .setFile(file.getBytes())
                .setMenu(fileMenu);
            fileMenuElements.add(fileMenuElement);
        }
        fileMenu
            .setElements(fileMenuElements)
            .setAttractionId(attractionId);
        return fileMenuRepository.save(fileMenu);
    }

    @Override
    public FileMenu findFileMenuByAttractionId(@NotNull Long attractionId) {
        return fileMenuRepository.findByAttractionId(attractionId);
    }

    @Override
    @NotNull
    public Menu getByMenuAttractionName(@NotNull String attractionName) {
        Long attractionId = attractionService.getByName(attractionName).getId();
        String type = attractionService.getTypeByName(attractionName);

        if ("cafe".equals(type) || "restaurant".equals(type)) {
            List<FileMenuElement> fileMenuElements = fileMenuRepository.findFileMenuElementsByAttractionId(attractionId);
            if (Objects.nonNull(fileMenuElements)) {
                FileMenu fileMenu = findFileMenuByAttractionId(attractionId);
                fileMenu.setElements(fileMenuElements);
                return fileMenu;
            }

            List<TextMenuElement> textMenuElements = textMenuRepository.findTextMenuElementsByAttractionId(attractionId);
            if (Objects.nonNull(textMenuElements)) {
                TextMenu textMenu = findTextMenuByAttractionId(attractionId);
                textMenu.setElements(textMenuElements);
                return textMenu;
            }
        }
        throw new EntityNotFoundException("No menu found for attraction: " + attractionName);
    }
}
