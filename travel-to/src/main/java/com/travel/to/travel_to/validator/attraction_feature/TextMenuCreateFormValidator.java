package com.travel.to.travel_to.validator.attraction_feature;

import com.travel.to.travel_to.form.attraction_feature.TextMenuCreateForm;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TextMenuCreateFormValidator implements Validator {

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return TextMenuCreateForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        TextMenuCreateForm form = (TextMenuCreateForm) target;

        if (
            form.getNames().size() != form.getDescriptions().size()
                || form.getNames().size() != form.getPrices().size()
                || form.getDescriptions().size() != form.getPrices().size()
        ) {
            errors.rejectValue("", "");
        }
    }
}
