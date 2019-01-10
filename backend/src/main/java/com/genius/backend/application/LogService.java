package com.genius.backend.application;

import com.genius.backend.domain.model.log.LogDto;

import java.util.List;

public interface LogService {

	List<LogDto.Response> findAll();
}
