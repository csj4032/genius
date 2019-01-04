package com.genius.backend.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class GeniusOncePerRequestFilter extends OncePerRequestFilter {

	public static final String REQUEST_AT = "request-at";
	public static final String EXECUTION_TIME = "execution-time";

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		log.info("GeniusOncePerRequestFilter");
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}