package com.genius.backend.infrastructure.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	@Before("@annotation(PreLogging)")
	public void preLogging(JoinPoint joinPoint) throws Throwable {
		System.out.println(joinPoint.getArgs());
	}
}