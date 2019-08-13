package com.genius.backend.infrastructure.security.social.provider;

import com.genius.backend.domain.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.Connection;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.social.kakao.api.impl.json.ButtonMixin;
import org.springframework.social.kakao.api.impl.json.LinkMixin;
import org.springframework.social.kakao.api.impl.json.ObjectTypeMixin;
import org.springframework.social.kakao.api.impl.json.TextMixin;

import java.util.List;

@Slf4j
public class KakaoSocialProvider implements SocialProvider<Kakao> {

	private Connection<Kakao> connection;
	private Kakao kakao;

	public KakaoSocialProvider(final Connection<Kakao> connection) {
		this.connection = connection;
		this.kakao = connection.getApi();
	}

	@Override
	public User getUser() {
		var user = getUser(connection);
		var profile = connection.fetchUserProfile();
		user.setEmail(profile.getEmail());
		return user;
	}

	@Override
	public String getProviderId() {
		return connection.getKey().getProviderId();
	}

	@Override
	public String getProviderUserId() {
		return connection.getKey().getProviderUserId();
	}

	@Override
	public void pushMessage(String message) {
		var link = new LinkMixin();
		var buttons = List.of(new ButtonMixin("Alimy", null));
		var text = new TextMixin(ObjectTypeMixin.TEXT, message, link, "Alimy", buttons);
		kakao.talkOperation().sendToMe(text.toString());
	}

	@Override
	public String getRefreshAccessToken() {
		return null;
	}

	@Override
	public String getAccessToken() {
		return connection.createData().getAccessToken();
	}

	public Connection<Kakao> getConnection() {
		return this.connection;
	}
}