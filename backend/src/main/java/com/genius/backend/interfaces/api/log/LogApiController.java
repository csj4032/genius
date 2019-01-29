package com.genius.backend.interfaces.api.log;

import com.genius.backend.application.LogService;
import com.genius.backend.domain.model.log.HttpRequestLog;
import com.genius.backend.domain.model.log.Log;
import com.genius.backend.domain.model.log.LogDto;
import com.genius.backend.domain.model.log.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LogApiController {

	@Autowired
	private LogService logService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/log")
	public List<Log> logs() {
		return logService.findByLogType(LogType.HTTP_REQUEST);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/log/last", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<LogDto.Response> logsFlux() {
		return logService.findLastLog(LogType.HTTP_REQUEST);
	}
}