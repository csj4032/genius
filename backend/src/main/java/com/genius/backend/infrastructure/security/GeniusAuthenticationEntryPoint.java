package com.genius.backend.infrastructure.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GeniusAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException ex) throws IOException, ServletException {
		httpServletRequest.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.FORBIDDEN.value());
		httpServletRequest.setAttribute("javax.servlet.error.message", HttpStatus.FORBIDDEN.getReasonPhrase());
		httpServletRequest.setAttribute("javax.servlet.error.exception", ex);
		var requestDispatcher = httpServletRequest.getRequestDispatcher("/error");
		requestDispatcher.forward(httpServletRequest, httpServletResponse);
	}
}