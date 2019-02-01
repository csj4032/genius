package com.genius.backend.infrastructure.security.social.provider;

import com.genius.backend.domain.model.user.User;

public interface SocialProvider {

	User getUser();

	String getProviderUserId();

	void pushMessage(String object);
}