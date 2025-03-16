package com.travel.to.travel_to.model;

import org.springframework.hateoas.RepresentationModel;

public class AttractionDiscussionModel extends RepresentationModel<AttractionDiscussionModel> {
    private String title;
    private String contentLike;
    private String contentDislike;
    private String content;
    private Double rating;

    public String getTitle() {
        return title;
    }

    public AttractionDiscussionModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContentLike() {
        return contentLike;
    }

    public AttractionDiscussionModel setContentLike(String contentLike) {
        this.contentLike = contentLike;
        return this;
    }

    public String getContentDislike() {
        return contentDislike;
    }

    public AttractionDiscussionModel setContentDislike(String contentDislike) {
        this.contentDislike = contentDislike;
        return this;
    }

    public String getContent() {
        return content;
    }

    public AttractionDiscussionModel setContent(String content) {
        this.content = content;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public AttractionDiscussionModel setRating(Double rating) {
        this.rating = rating;
        return this;
    }
}
