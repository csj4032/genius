package com.genius.backend.infrastructure.aspect;

import com.genius.backend.application.LogService;
import io.undertow.servlet.spec.HttpServletRequestImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	@Autowired
	private LogService logService;

	@Before(value = "@annotation(preLogging)", argNames = "joinPoint,preLogging")
	public void preLogging(JoinPoint joinPoint, PreLogging preLogging) {

	}
}