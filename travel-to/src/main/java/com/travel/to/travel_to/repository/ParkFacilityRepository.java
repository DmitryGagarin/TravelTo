package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.attraction_feature.facilities.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkFacilityRepository extends JpaRepository<Facility, Long> {

    Optional<List<Facility>> findAllByAttractionId(Long attractionId);

}
