package com.genius.backend.interfaces.log;

import com.genius.backend.application.LogService;
import com.genius.backend.domain.model.log.Log;
import com.genius.backend.domain.model.log.LogDto;
import com.genius.backend.domain.model.log.LogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogController {

	@Autowired
	private LogService logService;

	//@PreAuthorize("hasRole('ROLE_MANAGER')")
	@GetMapping("/log")
	public List<Log> logs() {
		return logService.findByLogType(LogType.HTTP_REQUEST);
	}
}