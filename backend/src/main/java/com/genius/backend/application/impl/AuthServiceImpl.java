package com.genius.backend.application.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.genius.backend.application.AuthService;
import com.genius.backend.application.UserService;
import com.genius.backend.domain.model.auth.AuthDto;
import com.genius.backend.domain.model.user.User;
import com.genius.backend.infrastructure.security.jwt.JwtTokenProvider;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.social.kakao.api.KakaoProfile;
import org.springframework.social.kakao.api.impl.KakaoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public AuthDto.Response auth(AuthDto.Request request) throws JsonProcessingException {
		var userOperation = new KakaoTemplate(request.getAccessToken()).userOperation();
		try {
			var profile = userOperation.getUserProfile();
			var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(profile.getId(), profile.getId(), Collections.emptyList()));
			var user = userSaveOrUpdate(request, profile, authentication);
			return AuthDto.Response.builder().userId(user.getId()).username(user.getUsername()).userImage(user.getImageUrl()).tokenType("Bearer").accessToken(tokenProvider.generateToken(user)).build();
		} catch (HttpClientErrorException | AuthenticationException e) {
			log.error(e.getMessage());
		}
		return null;


	}

	private User userSaveOrUpdate(AuthDto.Request request, KakaoProfile profile, Authentication authentication) {
		return (authentication.getPrincipal() == null) ? userService.save(request, profile) : userService.update(request, profile, ((GeniusSocialUserDetail) authentication.getPrincipal()).getUser());
	}
}