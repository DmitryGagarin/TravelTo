package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.attraction_feature.menu.menu.FileMenu;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu_element.FileMenuElement;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileMenuRepository extends JpaRepository<FileMenu, Long> {

    @Query(value = "SELECT * FROM file_menu_element WHERE attraction_id = :attractionId",
    nativeQuery = true)
    List<FileMenuElement> findFileMenuElementsByAttractionId(@NotNull Long attractionId);

    Optional<FileMenu> findByAttractionId(@NotNull Long attractionId);

}
