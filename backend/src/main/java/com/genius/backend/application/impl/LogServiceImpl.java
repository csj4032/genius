package com.genius.backend.application.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.backend.application.LogService;
import com.genius.backend.domain.mapper.LogMapper;
import com.genius.backend.domain.model.log.Log;
import com.genius.backend.domain.model.log.LogDto;
import com.genius.backend.domain.model.log.LogType;
import com.genius.backend.domain.repository.LogRepository;
import org.jetbrains.annotations.NotNull;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private LogMapper logMapper;

	@Autowired
	protected ObjectMapper objectMapper;

	@Override
	public void save(Log log) {
		logRepository.save(log);
	}

	@Override
	public List<LogDto.Response> findAll() {
		return logRepository.findAll().stream().map(getLogResponse()).collect(toList());
	}

	@Override
	public List<Log> findByLogType(LogType logType) {
		return logMapper.findByLogType(logType);
	}

	@NotNull
	private Function<Log, LogDto.Response> getLogResponse() {
		return Unchecked.function(e -> LogDto.Response.builder().id(e.getId()).type(e.getType()).value(objectMapper.readValue(e.getValue(), e.getType().getClazz())).build());
	}

	@Override
	public Flux<LogDto.Response> findLastLog(LogType logType) {
		return Flux.interval(Duration.ofSeconds(1)).map(Unchecked.function(source -> {
			var log = logRepository.findTop1ByTypeOrderByIdDesc(logType);
			return LogDto.Response.builder().id(log.getId()).type(log.getType()).value(objectMapper.readValue(log.getValue(), log.getType().getClazz())).build();
		})).log();
	}
}