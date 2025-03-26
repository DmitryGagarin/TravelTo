package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.attraction.AttractionDiscussion;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionDiscussionRepository extends JpaRepository<AttractionDiscussion, Long> {

    @NotNull
    AttractionDiscussion getByUuid(String attractionUuid);

    @NotNull
    @Query(value = "SELECT * FROM attraction_discussion WHERE attraction_id =:attractionId",
    nativeQuery = true)
    List<AttractionDiscussion> findAllByAttractionId(Long attractionId);

}
