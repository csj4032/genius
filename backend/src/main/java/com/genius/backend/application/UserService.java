package com.genius.backend.application;

import com.genius.backend.domain.model.auth.AuthDto;
import com.genius.backend.domain.model.user.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.kakao.api.AccessTokenInfo;
import org.springframework.social.kakao.api.KakaoProfile;

import java.util.Optional;

public interface UserService {

	boolean isUser(Connection<?> connection);

	Optional<User> findByConnection(Connection<?> connection);

	Optional<User> findByProviderUserId(String providerUserId);

	User save(User user);

	User save(AuthDto.Request request, KakaoProfile profile);

	User update(AuthDto.Request request, KakaoProfile profile, User user);

	void delete(User user);

	void deleteByIdForUnlink(User user);

	void refreshAccess();
}