package com.travel.to.travel_to.validator;

import com.travel.to.travel_to.form.UserSignInForm;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserSignInFormValidator implements Validator {

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return UserSignInForm.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {

    }
}
