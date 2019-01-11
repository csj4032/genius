package com.genius.backend.application.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.backend.application.LogService;
import com.genius.backend.domain.model.log.Log;
import com.genius.backend.domain.model.log.LogDto;
import com.genius.backend.domain.repository.LogRepository;
import org.jetbrains.annotations.NotNull;
import org.jooq.lambda.Unchecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogRepository logRepository;

	@Autowired
	protected ObjectMapper objectMapper;

	@Override
	public List<LogDto.Response> findAll() {
		return logRepository.findAll().stream().map(getLogResponse()).collect(toList());
	}

	@NotNull
	private Function<Log, LogDto.Response> getLogResponse() {
		return Unchecked.function(e -> LogDto.Response.builder().id(e.getId()).type(e.getType()).value(objectMapper.readValue(e.getValue(), e.getType().getClazz())).build());
	}
}