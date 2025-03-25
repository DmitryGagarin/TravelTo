package com.travel.to.travel_to.validator.attraction_discussion;

import com.travel.to.travel_to.constants.ValidationConstants;
import com.travel.to.travel_to.constants.ValidationErrorCodes;
import com.travel.to.travel_to.constants.ValidationFields;
import com.travel.to.travel_to.form.AttractionDiscussionCreateForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AttractionDiscussionCreateFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AttractionDiscussionCreateForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AttractionDiscussionCreateForm form = (AttractionDiscussionCreateForm) target;

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
