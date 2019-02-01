package com.genius.backend.infrastructure.security.social.provider;

import com.genius.backend.infrastructure.security.social.property.FacebookProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SocialProviderBuilder {

	private static FacebookProperties FACEBOOK_PROPERTIES;
	@Autowired
	private FacebookProperties facebookProperties;

	private SocialProviderBuilder() {
	}

	public static SocialProvider create(final Connection<?> connection) {
		if (connection.getKey().getProviderId().equals("kakao")) {
			return new KakaoSocialProvider(connection);
		} else if (connection.getKey().getProviderId().equals("line")) {
			return new LineSocialProvider(connection);
		} else if (connection.getKey().getProviderId().equals("facebook")) {
			return new FacebookSocialProvider(connection, FACEBOOK_PROPERTIES);
		} else {
			throw new IllegalArgumentException("Connection not supported");
		}
	}

	@PostConstruct
	public void init() {
		SocialProviderBuilder.FACEBOOK_PROPERTIES = facebookProperties;
	}
}