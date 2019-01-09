package com.genius.backend.application.impl;

import com.genius.backend.application.LogService;
import com.genius.backend.domain.model.log.HttpRequestLog;
import com.genius.backend.domain.model.log.Log;
import com.genius.backend.domain.model.log.LogType;
import com.genius.backend.domain.repository.LogRepository;
import com.genius.backend.infrastructure.aspect.PreLogging;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.function.Function;

@Slf4j
@Service
public class LogServiceImpl implements LogService {
	private LogRepository logRepository;
	private HttpServletRequest httpServletRequest;
	private JoinPoint joinPoint;

	public LogServiceImpl(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	@Override
	public void setJoinPoint(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
	}

	@Override
	public JoinPoint getJoinPoint() {
		return joinPoint;
	}

	public void write(LogType logType) {
		logType.getLogBindType().getFunction().apply(joinPoint);
	}
}