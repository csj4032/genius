package com.genius.backend.infrastructure.security.social;

import com.genius.backend.application.UserService;
import com.genius.backend.domain.repository.UserRepository;
import com.genius.backend.infrastructure.security.social.provider.SocialProviderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

@Slf4j
public class GeniusSignInAdapter implements SignInAdapter {

	@Autowired
	private UserService userService;

	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest nativeWebRequest) {
		nativeWebRequest.getNativeResponse();
		log.info("알리미 앱 로그인 providerId : {}, localUserId : {}", connection.getKey().getProviderId(), localUserId);
		var providerUserId = SocialProviderBuilder.create(connection).getProviderUserId();
		var user = userService.findByProviderUserId(providerUserId).get();
		var geniusSocialUserDetail = GeniusSocialUserDetail.create(user);
		var authentication = new UsernamePasswordAuthenticationToken(geniusSocialUserDetail, null, geniusSocialUserDetail.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(((ServletWebRequest) nativeWebRequest).getRequest()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return null;
	}
}