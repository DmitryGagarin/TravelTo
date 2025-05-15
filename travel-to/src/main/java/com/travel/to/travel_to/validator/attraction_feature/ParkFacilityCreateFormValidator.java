package com.travel.to.travel_to.validator.attraction_feature;

import com.travel.to.travel_to.form.attraction_feature.ParkFacilityCreateForm;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ParkFacilityCreateFormValidator implements Validator {

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return ParkFacilityCreateForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        ParkFacilityCreateForm form = (ParkFacilityCreateForm) target;
        if (
            form.getNames().size() != form.getDescriptions().size()
            || form.getNames().size() != form.getOpenTimes().size()
            || form.getOpenTimes().size() != form.getCloseTimes().size()
        ) {
            errors.rejectValue("", "");
        }
    }
}
