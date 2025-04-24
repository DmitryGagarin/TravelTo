package com.travel.to.travel_to.controller;

import com.travel.to.travel_to.assembler.UsabilityQuestionnaireModelAssembler;
import com.travel.to.travel_to.entity.user.AuthUser;
import com.travel.to.travel_to.form.UsabilityQuestionnaireForm;
import com.travel.to.travel_to.model.UsabilityQuestionnaireModel;
import com.travel.to.travel_to.service.QuestionnaireService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
    name = "Questionnaire controller",
    description = "Saves usability, availability metrics/user opinions"
)
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;
    private final UsabilityQuestionnaireModelAssembler usabilityQuestionnaireModelAssembler;

    @Autowired
    public QuestionnaireController(
        QuestionnaireService questionnaireService,
        UsabilityQuestionnaireModelAssembler usabilityQuestionnaireModelAssembler
    ) {
        this.questionnaireService = questionnaireService;
        this.usabilityQuestionnaireModelAssembler = usabilityQuestionnaireModelAssembler;
    }

    @PostMapping("/mark-usability")
    public UsabilityQuestionnaireModel saveUsability(
        @Validated UsabilityQuestionnaireForm usabilityQuestionnaireForm,
        BindingResult bindingResult,
        @AuthenticationPrincipal AuthUser authUser
    ) {
        return usabilityQuestionnaireModelAssembler.toModel(
            questionnaireService.createUsabilityQuestionnaireAnswer(
                usabilityQuestionnaireForm,
                authUser
            )
        );
    }

}
