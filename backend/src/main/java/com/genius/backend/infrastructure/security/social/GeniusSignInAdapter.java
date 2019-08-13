package com.genius.backend.infrastructure.security.social;

import com.genius.backend.application.UserService;
import com.genius.backend.application.exception.NotExistUserException;
import com.genius.backend.domain.model.user.User;
import com.genius.backend.infrastructure.security.social.provider.SocialProviderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Optional;

@Slf4j
public class GeniusSignInAdapter implements SignInAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	private SocialProviderBuilder socialProviderBuilder;

	@Override
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest nativeWebRequest) {
		log.info("알리미 앱 로그인 providerId : {}, localUserId : {}", connection.getKey().getProviderId(), localUserId);
		var socialProvider = socialProviderBuilder.create(connection.getKey());
		var user = userService.findByProviderIdAndProviderUserId(socialProvider.getProviderId(), socialProvider.getProviderUserId());
		if (user.isPresent()) {
			userSecurityUpdate((ServletWebRequest) nativeWebRequest, user);
			userSocialUpdate(connection, user);
		} else {
			throw new NotExistUserException(connection.getKey().getProviderId(), localUserId);
		}
		return null;
	}

	private void userSecurityUpdate(ServletWebRequest nativeWebRequest, Optional<User> user) {
		var geniusSocialUserDetail = GeniusSocialUserDetail.create(user.get());
		var authentication = new UsernamePasswordAuthenticationToken(geniusSocialUserDetail, null, geniusSocialUserDetail.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(nativeWebRequest.getRequest()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private void userSocialUpdate(Connection<?> connection, Optional<User> user) {
		var social = user.get().getUserSocial();
		var createData = connection.createData();
		social.setAccessToken(createData.getAccessToken());
		social.setRefreshToken(createData.getRefreshToken());
		social.setExpiredTime(createData.getExpireTime() == null ? 0 : createData.getExpireTime());
		userService.save(user.get());
	}
}