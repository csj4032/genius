package com.genius.backend.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class GeniusAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			log.warn("User: " + auth.getName() + " attempted to access the protected URL: " + httpServletRequest.getRequestURI());
		}
		httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error");
	}
}