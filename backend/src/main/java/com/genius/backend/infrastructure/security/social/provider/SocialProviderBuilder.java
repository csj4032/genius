package com.genius.backend.infrastructure.security.social.provider;

import com.genius.backend.application.ProviderType;
import com.genius.backend.domain.model.user.User;
import com.genius.backend.infrastructure.security.social.property.FacebookProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.*;
import org.springframework.social.connect.mem.InMemoryConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.social.line.api.Line;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocialProviderBuilder {

	@Autowired
	private FacebookProperties facebookProperties;

	@Autowired
	private ConnectionRepository inMemoryConnectionRepository;

	private SocialProviderBuilder() {
	}

	public SocialProvider create(final ConnectionKey connectionKey) {
		String providerId = connectionKey.getProviderId();
		if (providerId.equals("kakao")) {
			return new KakaoSocialProvider(inMemoryConnectionRepository.getConnection(Kakao.class, connectionKey.getProviderUserId()));
		} else if (providerId.equals("line")) {
			return new LineSocialProvider(inMemoryConnectionRepository.getConnection(Line.class, connectionKey.getProviderUserId()));
		} else if (providerId.equals("facebook")) {
			return new FacebookSocialProvider(inMemoryConnectionRepository.getConnection(Facebook.class, connectionKey.getProviderUserId()), facebookProperties);
		} else {
			throw new IllegalArgumentException("Connection not supported");
		}
	}

	public SocialProvider create(final User user) {
		if (user.getProviderType().equals(ProviderType.KAKAO)) {
			return new KakaoSocialProvider(inMemoryConnectionRepository.getConnection(Kakao.class, user.getProviderUserId()));
		} else if (user.getProviderType().equals(ProviderType.LINE)) {
			return new LineSocialProvider(inMemoryConnectionRepository.getConnection(Line.class, user.getProviderUserId()));
		} else if (user.getProviderType().equals(ProviderType.FACEBOOK)) {
			return new FacebookSocialProvider(inMemoryConnectionRepository.getConnection(Facebook.class, user.getProviderUserId()), facebookProperties);
		} else {
			throw new IllegalArgumentException("Connection not supported");
		}
	}
}