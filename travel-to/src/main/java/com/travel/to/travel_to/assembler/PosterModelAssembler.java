package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.entity.attraction_feature.poster.Poster;
import com.travel.to.travel_to.model.PosterModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PosterModelAssembler implements RepresentationModelAssembler<Poster, PosterModel> {

    public List<PosterModel> toModels(List<Poster> posters) {
        List<PosterModel> posterModels = new ArrayList<>();
        for (Poster poster : posters) {
            posterModels.add(toModel(poster));
        }
        return posterModels;
    }

    @Override
    @NotNull
    public PosterModel toModel(@NotNull Poster entity) {
        return new PosterModel()
            .setImage(entity.getImage())
            .setImageFormat(entity.getImageFormat());
    }

}
