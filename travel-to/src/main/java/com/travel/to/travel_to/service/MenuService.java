package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.attraction_feature.menu.menu.FileMenu;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu.TextMenu;
import com.travel.to.travel_to.form.attraction_feature.TextMenuCreateForm;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface MenuService {

    @NotNull
    TextMenu createTextMenu(
        @NotNull String attractionName,
        @NotNull TextMenuCreateForm form,
        @NotNull MultipartFile[] images
    ) throws IOException;

    Optional<TextMenu> findTextMenuByAttractionId(@NotNull Long attractionId);

    @NotNull
    FileMenu createFileMenu(
        @NotNull String attractionName,
        @NotNull MultipartFile[] files
    ) throws IOException;

    Optional<FileMenu> findFileMenuByAttractionId(@NotNull Long attractionId);

    @Nullable
    Optional<TextMenu> getTextMenuByAttractionName(@NotNull String attractionName);

    @Nullable
    Optional<FileMenu> getFileMenuByAttractionName(@NotNull String attractionName);

}
