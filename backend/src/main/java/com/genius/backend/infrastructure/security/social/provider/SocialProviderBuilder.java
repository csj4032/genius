package com.genius.backend.infrastructure.security.social.provider;

import org.springframework.social.connect.Connection;

public class SocialProviderBuilder {

	private SocialProviderBuilder() {
	}

	public static SocialProvider create(final Connection<?> connection) {
		if (connection.getKey().getProviderId().equals("kakao")) {
			return new KakaoSocialProvider(connection);
		} else if (connection.getKey().getProviderId().equals("line")) {
			return new LineSocialProvider(connection);
		} else {
			throw new IllegalArgumentException("Connection not supported");
		}
	}
}
