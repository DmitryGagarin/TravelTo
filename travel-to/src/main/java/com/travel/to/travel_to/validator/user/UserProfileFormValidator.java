package com.travel.to.travel_to.validator.user;

import com.travel.to.travel_to.form.UserProfileForm;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserProfileFormValidator implements Validator {

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return UserProfileForm.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {

    }
}
