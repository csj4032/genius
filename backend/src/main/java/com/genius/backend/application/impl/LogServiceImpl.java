package com.genius.backend.application.impl;

import com.genius.backend.application.LogService;
import com.genius.backend.domain.repository.LogRepository;
import com.genius.backend.infrastructure.aspect.PreLogging;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogRepository logRepository;

	@Override
	public void logWrite(PreLogging preLogging, JoinPoint joinPoint) {
		Arrays.stream(preLogging.params()).forEach(e -> {
			String log = e.getLogBindType().getFunction().apply(joinPoint);
		});
	}
}