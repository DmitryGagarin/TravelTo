package com.travel.to.travel_to.validator.user;

import com.travel.to.travel_to.form.user.UserProfileForm;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserProfileFormValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return UserProfileForm.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {

    }
}
