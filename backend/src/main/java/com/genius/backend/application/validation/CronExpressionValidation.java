package com.genius.backend.application.validation;

import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.cronutils.model.CronType.QUARTZ;

@Slf4j
public class CronExpressionValidation implements ConstraintValidator<CronExpressionAnnotation, String> {

	private CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));

	@Override
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) return false;
		try {
			parser.parse(value).validate();
			return true;
		} catch (IllegalArgumentException ex) {
			log.error("{}", ex.getLocalizedMessage());
			constraintValidatorContext.disableDefaultConstraintViolation();
			constraintValidatorContext.buildConstraintViolationWithTemplate(ex.getMessage()).addConstraintViolation();
		}
		return false;
	}
}