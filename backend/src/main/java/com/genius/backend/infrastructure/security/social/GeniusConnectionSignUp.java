package com.genius.backend.infrastructure.security.social;

import com.genius.backend.domain.model.auth.Role;
import com.genius.backend.domain.model.user.User;
import com.genius.backend.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.social.kakao.api.talkTemplate.TextObject;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class GeniusConnectionSignUp implements ConnectionSignUp {

	@Autowired
	private UserRepository userRepository;

	@Override
	public String execute(Connection<?> connection) {
		log.info("알리미 앱 가입 {} : {}, : {}", connection.createData().getProviderUserId(), connection.getDisplayName(), connection.createData().getAccessToken());
		userRepository.findByProviderUserId(connection.createData().getProviderUserId()).ifPresentOrElse(User::toString, () -> {
			userRepository.save(getUser(connection));
		});
		return connection.createData().getProviderUserId();
	}

	private User getUser(Connection<?> connection) {
		var user = new User();
		user.setProviderId(connection.createData().getProviderId());
		user.setProviderUserId(connection.createData().getProviderUserId());
		user.setUsername(connection.getDisplayName());
		user.setImageUrl(connection.createData().getImageUrl());
		user.setAccessToken(connection.createData().getAccessToken());
		user.setRefreshToken(connection.createData().getRefreshToken());
		user.setExpiredTime(connection.createData().getExpireTime());
		user.setRoles(Set.of(Role.builder().name("USER").build()));
		return user;
	}
}