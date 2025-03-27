package com.travel.to.travel_to.validator.user;

import com.travel.to.travel_to.form.UserSignUpSecondForm;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserSignUpSecondFormValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return UserSignUpSecondForm.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {

    }
}
