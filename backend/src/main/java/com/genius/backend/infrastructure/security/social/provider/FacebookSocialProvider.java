package com.genius.backend.infrastructure.security.social.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.Connection;

@Slf4j
public class FacebookSocialProvider implements SocialProvider {

	private Connection<?> connection;

	public FacebookSocialProvider(final Connection<?> connection) {
		this.connection = connection;
	}

	@Override
	public void sendMessage(String message) {
		log.warn("페이스북 메신저 -_-;;");
	}
}