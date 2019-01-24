package com.genius.backend.infrastructure.security.social.provider;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;

public class FacebookSocialProvider implements SocialProvider {

	private Facebook facebook;

	public FacebookSocialProvider(Connection<?> connection) {
		facebook = (Facebook) connection.getApi();
	}

	@Override
	public void sendMessage(String message) {

	}
}
