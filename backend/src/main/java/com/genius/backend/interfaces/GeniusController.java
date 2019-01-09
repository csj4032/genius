package com.genius.backend.interfaces;

import com.genius.backend.domain.model.log.LogType;
import com.genius.backend.infrastructure.aspect.PreLogging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class GeniusController {

	@GetMapping("/greeting")
	@PreLogging(params = {LogType.HTTP_REQUEST})
	public String greeting(HttpServletRequest httpServletRequest) {
		return "greeting";
	}
}