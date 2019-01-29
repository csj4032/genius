package com.genius.backend.infrastructure.security.social;

import com.genius.backend.application.ProviderType;
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
	private UserRepository userRepository;

	@Autowired
	SocialProviderBuilder socialProviderBuilder;

	@Override
	public String execute(Connection<?> connection) {
		Optional<User> userOptional = userRepository.findByProviderUserId(connection.createData().getProviderUserId());
		if (!userOptional.isPresent()) {
			log.info("알리미 앱 가입 {} : {} : {}", connection.createData().getProviderUserId(), connection.getDisplayName(), connection.createData().getAccessToken());
			userRepository.save(getUser(connection));
			socialProviderBuilder.create(connection).sendMessage("Welcome Alimy");
		}
		return connection.createData().getProviderUserId();
	}

	private User getUser(Connection<?> connection) {
		var user = new User();
		user.setProviderType(ProviderType.valueOf(connection.createData().getProviderId().toUpperCase()));
		user.setProviderUserId(connection.createData().getProviderUserId());
		user.setUsername(connection.getDisplayName());
		user.setImageUrl(connection.createData().getImageUrl());
		user.setAccessToken(connection.createData().getAccessToken());
		user.setRefreshToken(connection.createData().getRefreshToken());
		user.setExpiredTime(connection.createData().getExpireTime() == null ? 0l : connection.createData().getExpireTime());
		user.setRoles(Set.of(Role.builder().id(3l).name("USER").build()));
		return user;
	}
}