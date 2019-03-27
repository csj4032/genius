package com.genius.backend.application.impl;

import com.genius.backend.application.ProviderType;
import com.genius.backend.application.UserService;
import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.model.auth.AuthDto;
import com.genius.backend.domain.model.auth.Role;
import com.genius.backend.domain.model.user.User;
import com.genius.backend.domain.model.user.UserSocial;
import com.genius.backend.domain.repository.AlimyRepository;
import com.genius.backend.domain.repository.AlimyUnitRepository;
import com.genius.backend.domain.repository.UserRepository;
import com.genius.backend.domain.repository.UserSocialRepository;
import com.genius.backend.infrastructure.security.social.provider.SocialProvider;
import com.genius.backend.infrastructure.security.social.provider.SocialProviderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.kakao.api.KakaoProfile;
import org.springframework.social.kakao.connect.KakaoOAuth2Template;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

	@Value("${spring.social.kakao.appId}")
	private String appId;

	@Value("${spring.social.kakao.appSecret}")
	private String appSecret;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AlimyRepository alimyRepository;

	@Autowired
	private AlimyUnitRepository alimyUnitRepository;

	@Autowired
	private SocialProviderBuilder socialProviderBuilder;

	@Override
	public boolean isUser(Connection<?> connection) {
		return this.findByConnection(connection).isPresent();
	}

	@Override
	public Optional<User> findByConnection(Connection<?> connection) {
		return userRepository.findByProviderTypeAndProviderUserId(ProviderType.valueOf(connection.createData().getProviderId().toUpperCase()), connection.createData().getProviderUserId());
	}

	@Override
	public Optional<User> findByProviderIdAndProviderUserId(String providerId, String providerUserId) {
		return userRepository.findByProviderTypeAndProviderUserId(ProviderType.valueOf(providerId.toUpperCase()), providerUserId);
	}

	@Override
	public Optional<User> findByProviderUserId(String providerUserId) {
		return Optional.empty();
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User save(AuthDto.Request request, KakaoProfile profile) {
		var user = new User();
		user.setProviderUserId(Long.toString(profile.getId()));
		user.setUsername(profile.getProperties().getNickname());
		user.setImageUrl(profile.getProperties().getProfile_image());
		user.getUserSocial().setUser(user);
		user.getUserSocial().setAccessToken(request.getAccessToken());
		user.getUserSocial().setAccessToken(request.getAccessToken());
		user.getUserSocial().setRefreshToken(request.getRefreshToken());
		user.getUserSocial().setExpiredTime(request.getExpiresIn());
		user.setRoles(Set.of(Role.builder().id(3l).name("USER").build(), Role.builder().id(2l).name("MANAGER").build()));
		return userRepository.save(user);
	}

	@Override
	public User update(AuthDto.Request request, KakaoProfile profile, User user) {
		user.getUserSocial().setAccessToken(request.getAccessToken());
		user.getUserSocial().setRefreshToken(request.getRefreshToken());
		user.getUserSocial().setExpiredTime(request.getExpiresIn());
		user.setImageUrl(profile.getProperties().getProfile_image());
		user.setUsername(profile.getProperties().getNickname());
		return userRepository.save(user);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Override
	public void deleteByIdForUnlink(User user) {
		if (!user.getAlimys().isEmpty()) {
			alimyUnitRepository.deleteAlimyUnitByAlimyIds(user.getAlimys().stream().map(Alimy::getId).collect(toList()));
			alimyRepository.deleteByUserId(user.getId());
		}
		userRepository.delete(user);
	}

	@Override
	public void refreshAccess() {
		userRepository.findAll().stream().forEach(e -> {
			SocialProvider socialProvider = socialProviderBuilder.create(e);
			socialProvider.getRefreshAccessToken();
			userRepository.save(e);
		});
	}
}