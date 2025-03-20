package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.AttractionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionImageRepository extends JpaRepository<AttractionImage, Long> {

    @Query(value = "SELECT image FROM attraction_image WHERE attraction_id = :attractionId",
    nativeQuery = true)
    List<byte[]> getAllImagesByAttractionId(Long attractionId);

}
