package com.travel.to.travel_to.validator;

import com.travel.to.travel_to.form.UserProfileForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserProfileFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserProfileForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserProfileForm userProfileForm = (UserProfileForm) target;
    }
}
