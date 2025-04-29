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
    public TextMenu createTextMenu(
        @NotNull String attractionName,
        @NotNull TextMenuCreateForm form,
        @NotNull MultipartFile[] images
    ) throws IOException {
        List<TextMenuElement> textMenuElements = new ArrayList<>();
        Long attractionId = attractionService.getByName(attractionName).getId();
        for (int i = 0; i < form.getNames().size(); i++) {
            TextMenuElement textMenuElement = new TextMenuElement();
            textMenuElement.setAttractionId(attractionId);
            textMenuElement
                .setDishName(form.getNames().get(i))
                .setDishDescription(form.getDescriptions().get(i))
                .setDishPrice(form.getPrices().get(i))
                .setDishImage(images[i].getBytes());
        }
        TextMenu textMenu = new TextMenu();
        textMenu.setElements(textMenuElements);
        return textMenuRepository.save(textMenu);
    }

    @Override
    @NotNull
    public FileMenu createFileMenu(
        @NotNull String attractionName,
        @NotNull MultipartFile[] files
    ) throws IOException {
        List<FileMenuElement> fileMenuElements = new ArrayList<>();
        Long attractionId = attractionService.getByName(attractionName).getId();
        for (MultipartFile file : files) {
            FileMenuElement fileMenuElement = new FileMenuElement();
            fileMenuElement.setAttractionId(attractionId);
            fileMenuElement.setFile(file.getBytes());

        }
        FileMenu fileMenu = new FileMenu();
        fileMenu.setElements(fileMenuElements);
        return fileMenuRepository.save(fileMenu);
    }

    @Override
    @NotNull
    public Menu getByMenuAttractionName(@NotNull String attractionName) {
        Long attractionId = attractionService.getByName(attractionName).getId();
        String type = attractionService.getTypeByName(attractionName);

        if ("cafe".equals(type) || "restaurant".equals(type)) {
            FileMenu fileMenu = fileMenuRepository.findFileMenuByAttractionId(attractionId);
            if (Objects.nonNull(fileMenu)) {
                return fileMenu;
            }

            TextMenu textMenu = textMenuRepository.findTextMenuByAttractionId(attractionId);
            if (Objects.nonNull(textMenu)) {
                return textMenu;
            }
        }

        throw new EntityNotFoundException("No menu found for attraction: " + attractionName);
    }
}
