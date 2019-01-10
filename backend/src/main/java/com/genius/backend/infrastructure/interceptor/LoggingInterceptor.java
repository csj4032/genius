package com.genius.backend.infrastructure.interceptor;

import com.genius.backend.domain.model.log.*;
import com.genius.backend.domain.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

	@Autowired
	LogRepository logRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		var client = new Parser().parse(request.getHeader("User-Agent"));
		var value = HttpRequestLog.builder().path(request.getServletPath()).remoteAddr(request.getRemoteAddr()).device(client.device.family).os(client.os.family).browser(client.userAgent.family).build().toJson(new LogJsonValue());
		var gLog = Log.builder().type(LogType.HTTP_REQUEST).value(value).build();
		logRepository.save(gLog);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		var value = HttpResponseLog.builder().status(response.getStatus()).model(modelAndView.getModel()).viewName(modelAndView.getViewName()).build().toJson(new LogJsonValue());
		var gLog = Log.builder().type(LogType.HTTP_RESPONSE).value(value).build();
		logRepository.save(gLog);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

	}
}