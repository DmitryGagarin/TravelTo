package com.travel.to.travel_to.service;

import com.travel.to.travel_to.entity.questionnaire.UsabilityQuestionnaire;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.UsabilityQuestionnaireForm;
import org.jetbrains.annotations.NotNull;

public interface QuestionnaireService {

    @NotNull
    UsabilityQuestionnaire createUsabilityQuestionnaireAnswer(
        @NotNull UsabilityQuestionnaireForm usabilityQuestionnaireForm,
        @NotNull AuthUser authUser
    );

}
