package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.attraction_feature.menu.menu.TextMenu;
import com.travel.to.travel_to.entity.attraction_feature.menu.menu_element.TextMenuElement;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TextMenuRepository extends JpaRepository<TextMenu, Long> {

    @Query(value = "SELECT * FROM text_menu_element WHERE attraction_id = :attractionId",
    nativeQuery = true)
    List<TextMenuElement> findTextMenuElementsByAttractionId(@NotNull Long attractionId);

    TextMenu findByAttractionId(@NotNull Long attractionId);
}
