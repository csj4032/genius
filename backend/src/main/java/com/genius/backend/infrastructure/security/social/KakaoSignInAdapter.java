package com.genius.backend.infrastructure.security.social;

import com.genius.backend.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.web.context.request.NativeWebRequest;

@Slf4j
public class KakaoSignInAdapter implements SignInAdapter {

	private UserRepository userRepository;

	public KakaoSignInAdapter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest nativeWebRequest) {
		log.info("유저 로그인 성공 토큰 업데이트");
		log.info("kakao signin: {}", localUserId);
		GeniusUserDetail geniusUserDetail = createDetail(localUserId, connection);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(connection.getDisplayName(),  geniusUserDetail.getProviderUserId(), geniusUserDetail.getAuthorities()));
		Kakao kakao = (Kakao) connection.getApi();
		kakao.talkOperation().sendTalk("알리미 앱 로그인 성공");
		return "/";
	}

	private GeniusUserDetail createDetail (String localUserId, Connection<?> connection) {
		var user = userRepository.findByProviderUserId(localUserId).get();
		user.setAccessToken(connection.createData().getAccessToken());
		user.setRefreshToken(connection.createData().getRefreshToken());
		user.setExprietime(connection.createData().getExpireTime());
		userRepository.save(user);
		return GeniusUserDetail.create(user);
	}
}