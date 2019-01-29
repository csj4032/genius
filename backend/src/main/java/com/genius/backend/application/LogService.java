package com.genius.backend.application;

import com.genius.backend.domain.model.log.Log;
import com.genius.backend.domain.model.log.LogDto;
import com.genius.backend.domain.model.log.LogType;
import reactor.core.publisher.Flux;

import java.util.List;

public interface LogService {

	void save(Log log);

	List<LogDto.Response> findAll();

	List<Log> findByLogType(LogType logType);

	Flux<LogDto.Response> findLastLog(LogType logType);
}
