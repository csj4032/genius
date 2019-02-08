package com.genius.backend.infrastructure.security.social.provider;

import com.genius.backend.domain.model.user.User;
import com.genius.backend.infrastructure.security.social.property.FacebookProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class SocialProviderBuilder {

	@Autowired
	private FacebookProperties facebookProperties;

	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;

	private SocialProviderBuilder() {
	}

	public SocialProvider create(final Connection<?> connection) {
		if (connection.getKey().getProviderId().equals("kakao")) {
			return new KakaoSocialProvider(connection);
		} else if (connection.getKey().getProviderId().equals("line")) {
			return new LineSocialProvider(connection);
		} else if (connection.getKey().getProviderId().equals("facebook")) {
			return new FacebookSocialProvider(connection, facebookProperties);
		} else {
			throw new IllegalArgumentException("Connection not supported");
		}
	}

	public SocialProvider create(User user) {
		var providerId = user.getProviderType().getName();
		var userSocial = user.getUserSocial();
		var connectionData = new ConnectionData(providerId, user.getProviderUserId(), user.getUsername(), user.getImageUrl(), user.getImageUrl(), userSocial.getAccessToken(), null, userSocial.getRefreshToken(), userSocial.getExpiredTime());
		var connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);
		if (providerId.equals("kakao")) {
			return new KakaoSocialProvider(connection);
		} else if (providerId.equals("line")) {
			return new LineSocialProvider(connection);
		} else if (providerId.equals("facebook")) {
			return new FacebookSocialProvider(connection, facebookProperties);
		} else {
			throw new IllegalArgumentException("Connection not supported");
		}
	}
}