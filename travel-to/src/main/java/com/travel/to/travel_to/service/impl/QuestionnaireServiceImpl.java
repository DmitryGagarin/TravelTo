package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.questionnaire.UsabilityQuestionnaire;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.UsabilityQuestionnaireForm;
import com.travel.to.travel_to.repository.UsabilityQuestionnaireRepository;
import com.travel.to.travel_to.service.QuestionnaireService;
import com.travel.to.travel_to.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final UsabilityQuestionnaireRepository usabilityQuestionnaireRepository;
    private final UserService userService;

    @Autowired
    public QuestionnaireServiceImpl(
        UsabilityQuestionnaireRepository usabilityQuestionnaireRepository,
        UserService userService) {
        this.usabilityQuestionnaireRepository = usabilityQuestionnaireRepository;
        this.userService = userService;
    }

    @Override
    @NotNull
    public UsabilityQuestionnaire createUsabilityQuestionnaireAnswer(
        @NotNull UsabilityQuestionnaireForm usabilityQuestionnaireForm,
        @NotNull AuthUser authUser
    ) {
        return usabilityQuestionnaireRepository.save(
            new UsabilityQuestionnaire()
                .setAnswererId(userService.getByUuid(authUser.getUuid()).getId())
                .setQ1(usabilityQuestionnaireForm.getQ1())
                .setQ2(usabilityQuestionnaireForm.getQ2())
                .setQ3(usabilityQuestionnaireForm.getQ3())
                .setQ4(usabilityQuestionnaireForm.getQ4())
                .setQ5(usabilityQuestionnaireForm.getQ5())
                .setQ6(usabilityQuestionnaireForm.getQ6())
                .setQ7(usabilityQuestionnaireForm.getQ7())
                .setQ8(usabilityQuestionnaireForm.getQ8())
                .setQ9(usabilityQuestionnaireForm.getQ9())
                .setQ10(usabilityQuestionnaireForm.getQ10())
        );
    }
}
