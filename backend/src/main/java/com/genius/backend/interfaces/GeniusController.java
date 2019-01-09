package com.genius.backend.interfaces;

import com.genius.backend.infrastructure.aspect.PreLogging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Controller
public class GeniusController extends ResponseEntityExceptionHandler {

	@GetMapping("/greeting")
	@PreLogging
	public String greeting(HttpRequest httpRequest) {
		return "greeting";
	}
}