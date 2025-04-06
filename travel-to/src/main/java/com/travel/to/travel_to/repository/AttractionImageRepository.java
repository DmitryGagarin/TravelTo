package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.attraction.AttractionImage;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionImageRepository extends JpaRepository<AttractionImage, Long> {

    @NotNull
    @Query(value = "SELECT image FROM attraction_image WHERE attraction_id = :attractionId",
    nativeQuery = true)
    List<byte[]> getAllImagesByAttractionId(Long attractionId);

    @NotNull
    @Query(value = "SELECT image_format FROM attraction_image WHERE attraction_id = :attractionId",
    nativeQuery = true)
    List<String> getAllImagesFormatsByAttractionId(Long attractionId);

    @Modifying
    @Query(value = "DELETE FROM attraction_image WHERE attraction_id = :attractionId",
    nativeQuery = true)
    void deleteAllByAttractionId(Long attractionId);

}
