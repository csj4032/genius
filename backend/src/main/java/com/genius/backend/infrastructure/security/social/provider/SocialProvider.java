package com.genius.backend.infrastructure.security.social.provider;

import com.genius.backend.application.ProviderType;
import com.genius.backend.domain.model.auth.Role;
import com.genius.backend.domain.model.user.User;
import org.springframework.social.connect.Connection;

import java.util.Set;

public interface SocialProvider {

	User getUser();

	String getProviderId();

	String getProviderUserId();

	void pushMessage(String object);

	default User getUser(Connection<?> connection) {
		var user = new User();
		user.setProviderType(ProviderType.valueOf(connection.createData().getProviderId().toUpperCase()));
		user.setProviderUserId(connection.createData().getProviderUserId());
		user.setUsername(connection.getDisplayName());
		user.setImageUrl(connection.createData().getImageUrl());
		user.getUserSocial().setUser(user);
		user.getUserSocial().setAccessToken(connection.createData().getAccessToken());
		user.getUserSocial().setRefreshToken(connection.createData().getRefreshToken());
		user.getUserSocial().setExpiredTime(connection.createData().getExpireTime() == null ? 0l : connection.createData().getExpireTime());
		user.setRoles(Set.of(Role.builder().id(3l).name("USER").build()));
		return user;
	}
}