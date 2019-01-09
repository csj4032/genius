package com.genius.backend.application;

import com.genius.backend.infrastructure.aspect.PreLogging;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public interface LogService {

	void logWrite(PreLogging preLogging, JoinPoint joinPoint);
}
