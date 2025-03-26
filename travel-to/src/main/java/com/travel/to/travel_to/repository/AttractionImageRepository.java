package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.attraction.AttractionImage;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionImageRepository extends JpaRepository<AttractionImage, Long> {

    @NotNull
    @Query(value = "SELECT image FROM attraction_image WHERE attraction_id = :attractionId",
    nativeQuery = true)
    List<byte[]> getAllImagesByAttractionId(Long attractionId);

}
