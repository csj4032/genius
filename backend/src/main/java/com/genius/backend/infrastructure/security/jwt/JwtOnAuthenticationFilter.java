package com.genius.backend.infrastructure.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.backend.domain.model.auth.AuthDto;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Slf4j
public class JwtOnAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public JwtOnAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
		super(defaultFilterProcessesUrl);
		setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		try (InputStream inputStream = httpServletRequest.getInputStream()) {
			var authDtoRequest = new ObjectMapper().readValue(inputStream, AuthDto.Request.class);
			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(authDtoRequest.getAccessToken(), authDtoRequest, Collections.emptyList()));
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain, Authentication authentication) throws IOException {
		var user = ((GeniusSocialUserDetail) authentication.getPrincipal()).getUser();
		var authDtoResponse = AuthDto.Response.builder().userId(user.getId()).username(user.getUsername()).userImage(user.getImageUrl()).tokenType("Bearer").accessToken(jwtTokenProvider.generateToken(user)).build();
		var authDtoResponseString = new ObjectMapper().writeValueAsString(authDtoResponse);
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.getWriter().write(authDtoResponseString);
		httpServletResponse.flushBuffer();
	}
}