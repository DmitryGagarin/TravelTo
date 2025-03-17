package com.travel.to.travel_to.validator;

import com.travel.to.travel_to.constants.ValidationConstants;
import com.travel.to.travel_to.constants.ValidationErrorCodes;
import com.travel.to.travel_to.constants.ValidationFields;
import com.travel.to.travel_to.form.CreateAttractionDiscussionForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CreateAttractionDiscussionFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateAttractionDiscussionForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateAttractionDiscussionForm form = (CreateAttractionDiscussionForm) target;

        if (
            form.getContent().length() < ValidationConstants.ATTRACTION_DISCUSSION_MIN_LENGTH
            || form.getContentLike().length() < ValidationConstants.ATTRACTION_DISCUSSION_MIN_LENGTH
            || form.getContentDislike().length() < ValidationConstants.ATTRACTION_DISCUSSION_MIN_LENGTH
        ) {
            errors.rejectValue(
                ValidationFields.ATTRACTION_CONTENT,
                ValidationErrorCodes.ATTRACTION_DISCUSSION_CONTENT_TOO_SHORT
            );
        }

        if (form.getTitle().length() < ValidationConstants.ATTRACTION_DISCUSSION_MIN_LENGTH) {
            errors.rejectValue(
                ValidationFields.ATTRACTION_DISCUSSION_TITLE,
                ValidationErrorCodes.ATTRACTION_DISCUSSION_CONTENT_TOO_SHORT
            );
        }
    }
}
