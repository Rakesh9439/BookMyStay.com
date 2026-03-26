package com.auth_service.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {





        if (email == null || email.isBlank()) {
            return false;
        }

        return email.matches(EMAIL_REGEX);
    }


}
