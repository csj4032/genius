package com.genius.backend.infrastructure.security.social.provider;

import com.genius.backend.domain.model.user.UserSocial;

public interface SocialProvider {

	UserSocial getUserSocial();

	void pushMessage(String object);
}