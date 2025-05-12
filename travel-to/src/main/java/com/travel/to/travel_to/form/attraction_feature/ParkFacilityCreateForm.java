package com.travel.to.travel_to.form.attraction_feature;

import java.util.List;

public class ParkFacilityCreateForm {
    private List<String> name;
    private List<String> description;
    private List<byte[]> image;
    private List<String> openTime;
    private List<String> closeTime;

    public List<String> getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    public List<byte[]> getImage() {
        return image;
    }

    public List<String> getOpenTime() {
        return openTime;
    }

    public List<String> getCloseTime() {
        return closeTime;
    }
}
