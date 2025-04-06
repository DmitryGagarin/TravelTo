package com.travel.to.travel_to.validator.user;

import com.travel.to.travel_to.constants.ValidationErrorCodes;
import com.travel.to.travel_to.constants.ValidationFields;
import com.travel.to.travel_to.form.UserSignInForm;
import com.travel.to.travel_to.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserSignInFormValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserSignInFormValidator(
        UserService userService
    ) {
        this.userService = userService;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return UserSignInForm.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        UserSignInForm userSignInForm = (UserSignInForm) target;

        String email = userSignInForm.getEmail();

        if (userService.findByEmail(email).isEmpty()) {
            errors.rejectValue(ValidationFields.USER, ValidationErrorCodes.USER_NOT_EXISTS);
        }

    }
}
