package com.genius.backend.infrastructure.aspect;

import com.genius.backend.application.LogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	@Autowired
	private LogService logService;

	@Before(value = "@annotation(preLogging)")
	public void preLogging(JoinPoint joinPoint, PreLogging preLogging) {
		//logService.logWrite(preLogging, joinPoint);
	}
}