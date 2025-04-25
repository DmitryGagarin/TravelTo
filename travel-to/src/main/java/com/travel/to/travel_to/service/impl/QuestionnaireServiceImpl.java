package com.travel.to.travel_to.service.impl;

import com.travel.to.travel_to.entity.questionnaire.UsabilityQuestionnaire;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.entity.user.User;
import com.travel.to.travel_to.form.UsabilityQuestionnaireForm;
import com.travel.to.travel_to.repository.UsabilityQuestionnaireRepository;
import com.travel.to.travel_to.repository.UserRepository;
import com.travel.to.travel_to.service.QuestionnaireService;
import com.travel.to.travel_to.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final UsabilityQuestionnaireRepository usabilityQuestionnaireRepository;
    private final UserService userService;

    @Autowired
    public QuestionnaireServiceImpl(
        UsabilityQuestionnaireRepository usabilityQuestionnaireRepository,
        UserService userService, UserRepository userRepository
    ) {
        this.usabilityQuestionnaireRepository = usabilityQuestionnaireRepository;
        this.userService = userService;
    }

    @Override
    @NotNull
    @Transactional
    public UsabilityQuestionnaire createUsabilityQuestionnaireAnswer(
        @NotNull UsabilityQuestionnaireForm usabilityQuestionnaireForm,
        @NotNull AuthUser authUser
    ) {
        User user = userService.findByEmail(authUser.getEmail()).get();
        user.setAnsweredUsabilityQuestionnaire(true);
        userService.save(user);

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
