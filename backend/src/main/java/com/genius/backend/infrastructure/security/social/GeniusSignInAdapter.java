package com.genius.backend.infrastructure.security.social;

import com.genius.backend.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

@Slf4j
public class GeniusSignInAdapter implements SignInAdapter {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UsersConnectionRepository usersConnectionRepository;

	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest nativeWebRequest) {
		log.info("유저 로그인 성공 토큰 업데이트");
		log.info("{} signin : {}", connection.getKey().getProviderId(), localUserId);
		var geniusSocialUserDetail = createDetail(localUserId, connection);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(connection.getDisplayName(), geniusSocialUserDetail.getProviderUserId(), geniusSocialUserDetail.getAuthorities()));
		return "/";
	}

	private GeniusSocialUserDetail createDetail(String localUserId, Connection<?> connection) {
		var user = userRepository.findByProviderUserId(localUserId).orElseThrow(() -> new NullPointerException());
		user.setImageUrl(connection.getImageUrl());
		user.setUsername(connection.getDisplayName());
		//user.setAccessToken(connection.createData().getAccessToken());
		//user.setRefreshToken(connection.createData().getRefreshToken());
		//user.setExpiredTime(connection.createData().getExpireTime());
		userRepository.save(user);
		return GeniusSocialUserDetail.create(user);
	}
}