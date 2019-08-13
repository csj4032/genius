package com.genius.backend.infrastructure.security.social;

import com.genius.backend.application.UserService;
import com.genius.backend.infrastructure.security.social.provider.SocialProviderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GeniusConnectionSignUp implements ConnectionSignUp {

	@Autowired
	private UserService userService;

	@Autowired
	private SocialProviderBuilder socialProviderBuilder;

	@Override
	public String execute(Connection<?> connection) {
		var socialProvider =  socialProviderBuilder.create(connection.getKey());
		if (!userService.findByProviderIdAndProviderUserId(socialProvider.getProviderId(), socialProvider.getProviderUserId()).isPresent()) {
			log.info("알리미 앱 가입 {} : {} : {}", connection.createData().getProviderUserId(), connection.getDisplayName(), connection.createData().getAccessToken());
			userService.save(socialProvider.getUser());
		}
		return connection.createData().getProviderUserId();
	}
}