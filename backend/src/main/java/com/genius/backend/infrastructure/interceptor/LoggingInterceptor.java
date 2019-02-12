package com.genius.backend.infrastructure.interceptor;

import com.genius.backend.application.LogService;
import com.genius.backend.domain.model.log.HttpRequestLog;
import com.genius.backend.domain.model.log.Log;
import com.genius.backend.domain.model.log.LogJsonValue;
import com.genius.backend.domain.model.log.LogType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

	@Autowired
	private LogService logService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		var client = new Parser().parse(request.getHeader("User-Agent"));
		var remoteAddr = request.getHeader("X-FORWARDED-FOR");
		var value = HttpRequestLog.builder().path(request.getServletPath()).remoteAddr(remoteAddr).device(client.device.family).os(client.os.family).browser(client.userAgent.family).build();
		var gLog = Log.builder().type(LogType.HTTP_REQUEST).value(value.toJson(new LogJsonValue())).build();
		logService.save(gLog);
		return true;
	}
}