package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.entity.attraction_feature.poster.TheaterPoster;
import com.travel.to.travel_to.model.TheaterPosterModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PosterModelAssembler implements RepresentationModelAssembler<TheaterPoster, TheaterPosterModel> {

    public List<TheaterPosterModel> toModels(List<TheaterPoster> theaterPosters) {
        List<TheaterPosterModel> theaterPosterModels = new ArrayList<>();
        for (TheaterPoster theaterPoster : theaterPosters) {
            theaterPosterModels.add(toModel(theaterPoster));
        }
        return theaterPosterModels;
    }

    @Override
    @NotNull
    public TheaterPosterModel toModel(@NotNull TheaterPoster entity) {
        return new TheaterPosterModel()
            .setImage(entity.getImage())
            .setImageFormat(entity.getImageFormat());
    }

}
