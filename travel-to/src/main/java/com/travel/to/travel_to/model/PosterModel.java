package com.travel.to.travel_to.model;

import org.springframework.hateoas.RepresentationModel;

public class PosterModel extends RepresentationModel<PosterModel> {
    private byte[] image;
    private String imageFormat;

    public byte[] getImage() {
        return image;
    }

    public PosterModel setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public PosterModel setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
        return this;
    }
}
