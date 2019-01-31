package com.genius.backend.infrastructure.security.social;

import com.genius.backend.application.ProviderType;
import com.genius.backend.application.UserService;
import com.genius.backend.domain.model.auth.Role;
import com.genius.backend.domain.model.user.User;
import com.genius.backend.domain.repository.UserRepository;
import com.genius.backend.infrastructure.security.social.provider.SocialProviderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class GeniusConnectionSignUp implements ConnectionSignUp {

	@Autowired
	private UserService userService;

	@Autowired
	SocialProviderBuilder socialProviderBuilder;

	@Override
	public String execute(Connection<?> connection) {
		if (!userService.isUser(connection)) {
			log.info("알리미 앱 가입 {} : {} : {}", connection.createData().getProviderUserId(), connection.getDisplayName(), connection.createData().getAccessToken());
			userService.save(connection);
			socialProviderBuilder.create(connection).pushMessage("Welcome Alimy");
		}
		return connection.createData().getProviderUserId();
	}
}