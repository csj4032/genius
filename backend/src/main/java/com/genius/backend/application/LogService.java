package com.genius.backend.application;

import com.genius.backend.domain.model.log.Log;
import com.genius.backend.domain.model.log.LogDto;

import java.util.List;

public interface LogService {

	void save(Log log);

	List<LogDto.Response> findAll();
}
