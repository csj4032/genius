package com.genius.backend.infrastructure.aspect;

import com.genius.backend.domain.model.log.LogBindType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreLogging {

	LogBindType[] types() default {};
}
