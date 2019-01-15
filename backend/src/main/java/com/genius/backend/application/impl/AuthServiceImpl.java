package com.genius.backend.application.impl;

import com.genius.backend.application.AuthService;
import com.genius.backend.application.UserService;
import com.genius.backend.domain.model.auth.AuthDto;
import com.genius.backend.domain.model.user.User;
import com.genius.backend.infrastructure.security.JwtTokenProvider;
import com.genius.backend.infrastructure.security.social.GeniusUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.kakao.api.AccessTokenInfo;
import org.springframework.social.kakao.api.KakaoProfile;
import org.springframework.social.kakao.api.impl.KakaoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

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
	public AuthDto.Response auth(AuthDto.Request request) {
		var userOperation = new KakaoTemplate(request.getAccessToken()).userOperation();
		try {
			var accessTokenInfo = userOperation.accessTokenInfo();
			var profile = userOperation.getUserProfile();
			var user = userSaveOrUpdate(request, accessTokenInfo, profile, userService.findByProviderUserId(profile.getId()));
			var userDetails = GeniusUserDetail.create(user);
			//Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getId(), userDetails.getPassword(), userDetails.getAuthorities()));
			var authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return AuthDto.Response.builder().userId(user.getId()).username(user.getUsername()).userImage(user.getImageUrl()).tokenType("Bearer").accessToken(tokenProvider.generateToken(authentication)).build();
		} catch (HttpClientErrorException | AuthenticationException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	private User userSaveOrUpdate(AuthDto.Request request, AccessTokenInfo accessTokenInfo, KakaoProfile profile, Optional<User> userOptional) {
		if (userOptional.isPresent()) return userService.update(request, profile, userOptional.get());
		return userService.save(request, accessTokenInfo, profile);
	}
}