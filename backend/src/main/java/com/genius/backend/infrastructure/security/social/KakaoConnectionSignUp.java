package com.genius.backend.infrastructure.security.social;

import com.genius.backend.domain.model.user.User;
import com.genius.backend.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KakaoConnectionSignUp implements ConnectionSignUp {

	@Autowired
	private UserRepository userRepository;

	@Override
	public String execute(Connection<?> connection) {
		log.info("알리미 앱 가입 {} : {}, : {}", connection.createData().getProviderUserId(), connection.getDisplayName(), connection.createData().getAccessToken());
		userRepository.findByProviderUserId(connection.createData().getProviderUserId()).ifPresentOrElse(User::toString, () -> {
			userRepository.save(getUser(connection));
			Kakao kakao = (Kakao) connection.getApi();
			kakao.talkOperation().sendTalk("알리미 앱 가입 성공");
		});
		return connection.createData().getProviderUserId();
	}

	private User getUser(Connection<?> connection) {
		var user = new User();
		user.setProviderUserId(connection.createData().getProviderUserId());
		user.setUsername(connection.getDisplayName());
		user.setImageUrl(connection.createData().getImageUrl());
		user.setAccessToken(connection.createData().getAccessToken());
		user.setRefreshToken(connection.createData().getRefreshToken());
		user.setExpiredTime(connection.createData().getExpireTime());
		return user;
	}
}