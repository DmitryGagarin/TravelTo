package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.attraction_feature.menu.menu.TextMenu;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TextMenuRepository extends JpaRepository<TextMenu, Long> {

    @Query(value = "SELECT * FROM text_menu WHERE attraction_id =: attractionId",
        nativeQuery = true)
    TextMenu findTextMenuByAttractionId(@NotNull Long attractionId);
}
