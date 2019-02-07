package com.genius.backend.infrastructure.security.social;

import com.genius.backend.application.UserService;
import com.genius.backend.application.exception.NotExistUserException;
import com.genius.backend.infrastructure.security.social.provider.SocialProviderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

@Slf4j
public class GeniusSignInAdapter implements SignInAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	SocialProviderBuilder socialProviderBuilder;

	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest nativeWebRequest) {
		log.info("알리미 앱 로그인 providerId : {}, localUserId : {}", connection.getKey().getProviderId(), localUserId);
		var socialProvider = socialProviderBuilder.create(connection);
		var user = userService.findByProviderIdAndProviderUserId(socialProvider.getProviderId(), socialProvider.getProviderUserId());
		if (user.isPresent()) {
			var geniusSocialUserDetail = GeniusSocialUserDetail.create(user.get());
			var authentication = new UsernamePasswordAuthenticationToken(geniusSocialUserDetail, null, geniusSocialUserDetail.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(((ServletWebRequest) nativeWebRequest).getRequest()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			throw new NotExistUserException(connection.getKey().getProviderId(), localUserId);
		}
		return null;
	}
}