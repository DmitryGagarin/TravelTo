package com.travel.to.travel_to.validator;

import com.travel.to.travel_to.constants.ValidationConstants;
import com.travel.to.travel_to.constants.ValidationErrorCodes;
import com.travel.to.travel_to.constants.ValidationFields;
import com.travel.to.travel_to.form.UserSignUpForm;
import com.travel.to.travel_to.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class UserSignUpFormValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserSignUpFormValidator(
        UserService userService
    ) {
        this.userService = userService;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return UserSignUpForm.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        UserSignUpForm userSignupForm = (UserSignUpForm) target;

        String password = userSignupForm.getPassword();

        if (Objects.isNull(userSignupForm.getPassword())) {
            errors.rejectValue(ValidationFields.PASSWORD,  ValidationErrorCodes.PASSWORD_IS_REQUIRED);
        }

        if (Objects.nonNull(password) && password.length() < ValidationConstants.USER_PASSWORD_MIN_LENGTH) {
            errors.rejectValue(ValidationFields.PASSWORD, ValidationErrorCodes.PASSWORD_TOO_SHORT);
        }

        if (Objects.isNull(userSignupForm.getEmail())) {
            errors.rejectValue(ValidationFields.EMAIL,  ValidationErrorCodes.EMAIL_IS_REQUIRED);
        }

        if (userService.findUserByEmail(userSignupForm.getEmail()).isPresent()) {
            errors.rejectValue(ValidationFields.EMAIL, ValidationErrorCodes.EMAIL_ALREADY_EXISTS);
        }
    }
}
