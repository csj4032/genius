package com.genius.backend.application.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CronExpressionValidation.class)
public @interface CronExpressionAnnotation  {

	String message() default "{alimy.requestFroSave.validation.cronExpressionValidation.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
