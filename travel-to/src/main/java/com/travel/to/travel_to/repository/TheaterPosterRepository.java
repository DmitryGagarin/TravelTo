package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.attraction_feature.poster.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterPosterRepository extends JpaRepository<Poster, Long> {
}
