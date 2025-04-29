package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.attraction_feature.menu.menu.FileMenu;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMenuRepository extends JpaRepository<FileMenu, Long> {

    @Query(value = "SELECT * FROM file_menu WHERE attraction_id =: attractionId",
    nativeQuery = true)
    FileMenu findFileMenuByAttractionId(@NotNull Long attractionId);

}
