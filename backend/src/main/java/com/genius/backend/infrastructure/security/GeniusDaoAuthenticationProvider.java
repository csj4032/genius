package com.genius.backend.infrastructure.security;

import com.genius.backend.application.UserService;
import com.genius.backend.domain.model.auth.AuthDto;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.kakao.api.KakaoProfile;
import org.springframework.social.kakao.api.impl.KakaoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Slf4j
@Component
public class GeniusDaoAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) {
		String kakaoToken = authentication.getPrincipal() == null ? "NONE_PROVIDED" : authentication.getName();
		GeniusSocialUserDetail userDetails = null;
		KakaoProfile profile = null;
		try {
			profile = new KakaoTemplate(kakaoToken).userOperation().getUserProfile();
			userDetails = (GeniusSocialUserDetail) userDetailsService.loadUserByUsername(Long.toString(profile.getId()));
			userService.update((AuthDto.Request) authentication.getCredentials(), profile, userDetails.getUser());
		} catch (UsernameNotFoundException ex) {
			log.info("{} 미사용자 신규 가입 진행", ex.getLocalizedMessage());
			var user = userService.save((AuthDto.Request) authentication.getCredentials(), profile);
			userDetails = GeniusSocialUserDetail.create(user);
		} catch (RestClientException ex) {
			log.error(ex.getLocalizedMessage());
		}
		return createSuccessAuthentication(userDetails, authentication, userDetails);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
		var result = new UsernamePasswordAuthenticationToken(principal, authentication.getCredentials(), user.getAuthorities());
		result.setDetails(authentication.getDetails());
		return result;
	}
}