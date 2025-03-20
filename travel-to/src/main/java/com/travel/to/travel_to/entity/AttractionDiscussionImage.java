package com.travel.to.travel_to.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "attraction_discussion_image")
public class AttractionDiscussionImage extends UuidAbleEntity {
    private Long attractionDiscussionId;
    private byte[] image;

    public Long getAttractionDiscussionId() {
        return attractionDiscussionId;
    }

    public AttractionDiscussionImage setAttractionDiscussionId(Long attractionDiscussionId) {
        this.attractionDiscussionId = attractionDiscussionId;
        return this;
    }

    public byte[] getImage() {
        return image;
    }

    public AttractionDiscussionImage setImage(byte[] image) {
        this.image = image;
        return this;
    }
}
