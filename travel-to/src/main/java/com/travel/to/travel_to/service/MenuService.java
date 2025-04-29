package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.attraction_feature.menu.menu.FileMenu;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu.Menu;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu.TextMenu;
import com.travel.to.travel_to.form.attraction_feature.TextMenuCreateForm;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MenuService {

    @NotNull
    TextMenu createTextMenu(
        @NotNull String attractionName,
        @NotNull TextMenuCreateForm form,
        @NotNull MultipartFile[] images
    ) throws IOException;

    @NotNull
    FileMenu createFileMenu(
        @NotNull String attractionName,
        @NotNull MultipartFile[] files
    ) throws IOException;

    @NotNull
    Menu getByMenuAttractionName(@NotNull String attractionName);
}
