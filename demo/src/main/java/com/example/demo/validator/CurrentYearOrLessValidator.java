package com.example.demo.validator;

import com.example.demo.annotation.CurrentYearOrLess;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class CurrentYearOrLessValidator implements ConstraintValidator<CurrentYearOrLess, Integer> {
    @Override
    public void initialize(CurrentYearOrLess constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext constraintValidatorContext) {
        return year == null || year <= Year.now().getValue();
    }
}
