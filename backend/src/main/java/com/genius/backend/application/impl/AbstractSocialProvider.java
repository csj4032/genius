package com.genius.backend.application.impl;

import com.genius.backend.application.SocialProvider;
import com.genius.backend.domain.model.alimy.Alimy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;

public abstract class AbstractSocialProvider implements SocialProvider {

	@Autowired
	private UsersConnectionRepository usersConnectionRepository;

	protected abstract void onSendAlimy(Alimy alimy);

	public Connection<?> getConnection (Connection<?> connection) {
		return usersConnectionRepository.createConnectionRepository(connection.getKey().getProviderUserId()).getConnection(connection.getKey());
	}
}
