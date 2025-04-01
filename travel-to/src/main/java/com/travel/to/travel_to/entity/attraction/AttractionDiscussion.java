package com.travel.to.travel_to.entity.attraction;

import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.entity.common.UuidAbleTimedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "attraction_discussion")
public class AttractionDiscussion extends UuidAbleTimedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5791404420055931257L;

    private String title;
    private String contentLike;
    private String contentDislike;
    private String content;
    private Double rating;
    private Long attractionId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    public String getContentLike() {
        return contentLike;
    }

    public AttractionDiscussion setContentLike(String contentLike) {
        this.contentLike = contentLike;
        return this;
    }

    public String getContentDislike() {
        return contentDislike;
    }

    public AttractionDiscussion setContentDislike(String contentDislike) {
        this.contentDislike = contentDislike;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AttractionDiscussion setContent(String content) {
        this.content = content;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public AttractionDiscussion setRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AttractionDiscussion setTitle(String title) {
        this.title = title;
        return this;
    }

    public User getAuthor() {
        return author;
    }

    public AttractionDiscussion setAuthorId(User authorId) {
        this.author = authorId;
        return this;
    }

    public Long getAttractionId() {
        return attractionId;
    }

    public AttractionDiscussion setAttractionId(Long attractionId) {
        this.attractionId = attractionId;
        return this;
    }
}
