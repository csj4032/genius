package com.genius.backend.infrastructure.security.social;

import com.genius.backend.domain.repository.UserRepository;
import com.genius.backend.infrastructure.security.social.provider.SocialProviderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

@Slf4j
public class GeniusSignInAdapter implements SignInAdapter {

	@Autowired
	private UserRepository userRepository;

	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest nativeWebRequest) {
		log.info("getProviderId : {}, localUserId : {}", connection.getKey().getProviderId(), localUserId);
		var geniusSocialUserDetail = updateGeniusSocialUserDetail(localUserId, connection);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(connection.getDisplayName(), geniusSocialUserDetail.getProviderUserId(), geniusSocialUserDetail.getAuthorities()));
		SocialProviderBuilder.create(connection).sendMessage("Login Success");
		return "/greeting";
	}

	private GeniusSocialUserDetail updateGeniusSocialUserDetail(String localUserId, Connection<?> connection) {
		var user = userRepository.findByProviderUserId(localUserId).orElseThrow(() -> new NullPointerException());
		user.setImageUrl(connection.getImageUrl());
		user.setUsername(connection.getDisplayName());
		userRepository.save(user);
		return GeniusSocialUserDetail.create(user);
	}
}