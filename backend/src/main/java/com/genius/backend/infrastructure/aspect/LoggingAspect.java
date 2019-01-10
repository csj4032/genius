package com.genius.backend.infrastructure.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	@Before(value = "@annotation(preLogging)", argNames = "joinPoint,preLogging")
	public void preLogging(JoinPoint joinPoint, PreLogging preLogging) {

	}
}