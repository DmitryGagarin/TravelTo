package com.travel.to.travel_to.repository;

import com.travel.to.travel_to.entity.attraction.AttractionDiscussionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionDiscussionImageRepository extends JpaRepository<AttractionDiscussionImage, Long> {

    @Query(value = "SELECT image FROM attraction_discussion_image WHERE attraction_discussion_id = :discussionId",
    nativeQuery = true)
    List<byte[]> getAllImagesByDiscussionId(Long discussionId);

}
