package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.attraction_feature.poster.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Long> {
    Optional<List<Poster>> findAllByAttractionId(Long attractionId);

    void deleteByAttractionId(Long attractionId);
}
