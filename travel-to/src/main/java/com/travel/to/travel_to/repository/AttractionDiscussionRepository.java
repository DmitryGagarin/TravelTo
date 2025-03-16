package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.AttractionDiscussion;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionDiscussionRepository extends JpaRepository<AttractionDiscussion, Long> {
    @NotNull
    AttractionDiscussion getByUuid(String attractionUuid);
}
