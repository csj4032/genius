package com.genius.backend.infrastructure.security.social.provider;

import com.genius.backend.domain.model.user.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.social.kakao.api.impl.json.ButtonMixin;
import org.springframework.social.kakao.api.impl.json.LinkMixin;
import org.springframework.social.kakao.api.impl.json.ObjectTypeMixin;
import org.springframework.social.kakao.api.impl.json.TextMixin;

import java.util.List;

public class KakaoSocialProvider implements SocialProvider {

	private Kakao kakao;

	public KakaoSocialProvider(final Connection<?> connection) {
		this.kakao = (Kakao) connection.getApi();
	}

	@Override
	public User getUser() {
		return null;
	}

	@Override
	public String getProviderUserId() {
		return null;
	}

	@Override
	public void pushMessage(String message) {
		var link = new LinkMixin();
		var buttons = List.of(new ButtonMixin("Alimy", null));
		var text = new TextMixin(ObjectTypeMixin.TEXT, message, link, "Alimy", buttons);
		kakao.talkOperation().send(text);
	}
}