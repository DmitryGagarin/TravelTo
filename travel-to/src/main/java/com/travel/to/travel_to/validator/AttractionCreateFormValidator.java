package com.travel.to.travel_to.validator;

import com.travel.to.travel_to.constants.ValidationErrorCodes;
import com.travel.to.travel_to.constants.ValidationFields;
import com.travel.to.travel_to.form.AttractionCreateForm;
import com.travel.to.travel_to.service.AttractionService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class AttractionCreateFormValidator implements Validator {

    private final AttractionService attractionService;

    @Autowired
    public AttractionCreateFormValidator(
        AttractionService attractionService
    ) {
        this.attractionService = attractionService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AttractionCreateForm.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        AttractionCreateForm attractionCreateForm = (AttractionCreateForm) target;

        if (attractionService.findByName(attractionCreateForm.getName()).isPresent()) {
            errors.rejectValue(ValidationFields.ATTRACTION_NAME, ValidationErrorCodes.ATTRACTION_ALREADY_EXISTS);
        }
    }
}
