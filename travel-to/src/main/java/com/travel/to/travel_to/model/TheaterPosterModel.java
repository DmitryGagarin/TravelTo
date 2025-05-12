package com.travel.to.travel_to.model;

import org.springframework.hateoas.RepresentationModel;

public class TheaterPosterModel extends RepresentationModel<TheaterPosterModel> {
    private byte[] image;
    private String imageFormat;

    public byte[] getImage() {
        return image;
    }

    public TheaterPosterModel setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public TheaterPosterModel setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
        return this;
    }
}
