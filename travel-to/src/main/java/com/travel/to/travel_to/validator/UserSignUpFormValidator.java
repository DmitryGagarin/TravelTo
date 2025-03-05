package com.travel.to.travel_to.validator;

import com.travel.to.travel_to.constants.ValidationDefaultMessages;
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

        if (Objects.isNull(userSignupForm.getUsername())) {
            errors.reject(ValidationFields.USERNAME, ValidationDefaultMessages.USERNAME_IS_REQUIRED);
        }

        if (Objects.isNull(userSignupForm.getPassword())) {
            errors.reject(ValidationFields.PASSWORD,  ValidationDefaultMessages.PASSWORD_IS_REQUIRED);
        }

        if (Objects.isNull(userSignupForm.getEmail())) {
            errors.reject(ValidationFields.EMAIL,  ValidationDefaultMessages.EMAIL_IS_REQUIRED);
        }

//        if (Objects.nonNull(userService.findUserByEmail(userSignupForm.getEmail()))) {
//            errors.reject(ValidationFields.EMAIL,  ValidationDefaultMessages.EMAIL_ALREADY_EXISTS);
//        }
    }
}
