package com.genius.backend.application.validation;

import org.quartz.CronExpression;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CronExpressionValidation implements ConstraintValidator<CronExpressionAnnotation, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) return false;
		return CronExpression.isValidExpression(value);
	}
}
