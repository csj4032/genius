package com.genius.backend.interfaces.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1")
public class TicktackController {

	@GetMapping(value = "/ticktack", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> logsFlux() {
		return Flux.interval(Duration.ofSeconds(1)).map(source -> LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).log();
	}
}