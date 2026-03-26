package com.auth_service.validation;


import jakarta.validation.Constraint;


import java.lang.annotation.*;

import jakarta.validation.Payload;


@Documented
@Constraint(validatedBy = com.auth_service.validation.EmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface  ValidEmail {

    String message() default "Invalid email format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
