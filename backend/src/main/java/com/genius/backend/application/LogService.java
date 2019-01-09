package com.genius.backend.application;

import com.genius.backend.domain.model.log.LogType;
import org.aspectj.lang.JoinPoint;

public interface LogService {

	void setJoinPoint(JoinPoint point);

	JoinPoint getJoinPoint();

	void write(LogType logType);
}
