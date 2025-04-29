package com.travel.to.travel_to.validator.attraction;

import com.travel.to.travel_to.form.attraction.AttractionEditForm;
import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class AttractionEditFormValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return AttractionEditForm.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {

    }
}
