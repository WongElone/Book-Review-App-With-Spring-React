package com.example.demo.annotation;

import com.example.demo.validator.CurrentYearOrLessValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrentYearOrLessValidator.class)
public @interface CurrentYearOrLess {
    String message() default "Year must be current year or less";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
