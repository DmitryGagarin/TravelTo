package com.travel.to.travel_to.validator.user;

import com.travel.to.travel_to.constants.ValidationConstants;
import com.travel.to.travel_to.constants.ValidationErrorCodes;
import com.travel.to.travel_to.constants.ValidationFields;
import com.travel.to.travel_to.form.UserSignUpFirstForm;
import com.travel.to.travel_to.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class UserSignUpFirstFormValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserSignUpFirstFormValidator(
        UserService userService
    ) {
        this.userService = userService;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return UserSignUpFirstForm.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        UserSignUpFirstForm userSignupFormFirst = (UserSignUpFirstForm) target;

        String password = userSignupFormFirst.getPassword();
        String email = userSignupFormFirst.getEmail();
        String emailDomain = email.substring(email.lastIndexOf('.') + 1);

        if (Objects.isNull(userSignupFormFirst.getPassword())) {
            errors.rejectValue(ValidationFields.PASSWORD,  ValidationErrorCodes.PASSWORD_IS_REQUIRED);
        }

        if (!ValidationConstants.ALLOWED_EMAIL_DOMAINS.contains(emailDomain)) {
            errors.rejectValue(ValidationFields.EMAIL, ValidationErrorCodes.EMAIL_PROHIBITED_DOMAIN);
        }

        if (Objects.nonNull(password) && password.length() < ValidationConstants.USER_PASSWORD_MIN_LENGTH) {
            errors.rejectValue(ValidationFields.PASSWORD, ValidationErrorCodes.PASSWORD_TOO_SHORT);
        }

        if (userService.findByEmail(email).isPresent()) {
            errors.rejectValue(ValidationFields.EMAIL, ValidationErrorCodes.EMAIL_ALREADY_EXISTS);
        }
    }
}
