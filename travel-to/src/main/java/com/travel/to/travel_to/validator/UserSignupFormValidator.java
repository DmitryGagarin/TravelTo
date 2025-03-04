package com.travel.to.travel_to.validator;

import com.travel.to.travel_to.constants.ValidationFields;
import com.travel.to.travel_to.form.UserSignupForm;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class UserSignupFormValidator implements Validator {

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return UserSignupForm.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        UserSignupForm userSignupForm = (UserSignupForm) target;

        if (Objects.isNull(userSignupForm.getUsername())) {
            errors.reject(ValidationFields.USERNAME,  "Username is required");
        }

        if (Objects.isNull(userSignupForm.getPassword())) {
            errors.reject(ValidationFields.PASSWORD,  "Password is required");
        }

        if (Objects.isNull(userSignupForm.getEmail())) {
            errors.reject(ValidationFields.EMAIL,  "Email is required");
        }
    }
}
