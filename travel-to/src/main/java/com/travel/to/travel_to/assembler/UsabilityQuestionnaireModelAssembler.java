package com.travel.to.travel_to.assembler;

import com.travel.to.travel_to.entity.questionnaire.UsabilityQuestionnaire;
import com.travel.to.travel_to.model.UsabilityQuestionnaireModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UsabilityQuestionnaireModelAssembler implements RepresentationModelAssembler<UsabilityQuestionnaire, UsabilityQuestionnaireModel> {

    @Override
    @NotNull
    public UsabilityQuestionnaireModel toModel(@NotNull UsabilityQuestionnaire entity) {
        return new UsabilityQuestionnaireModel()
            .setQ1(entity.getQ1())
            .setQ2(entity.getQ2())
            .setQ3(entity.getQ3())
            .setQ4(entity.getQ4())
            .setQ5(entity.getQ5())
            .setQ6(entity.getQ6())
            .setQ7(entity.getQ7())
            .setQ8(entity.getQ8())
            .setQ9(entity.getQ9())
            .setQ10(entity.getQ10());
    }
}
