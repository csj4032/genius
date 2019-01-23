package com.genius.backend.infrastructure.security.social.provider;

import org.springframework.social.connect.Connection;
import org.springframework.social.line.api.Line;
import org.springframework.social.line.api.impl.json.PushMessageMixin;
import org.springframework.social.line.api.impl.json.TextMessageMixin;


public class LineSocialProvider implements SocialProvider {

	private String providerUserId;
	private Line line;

	public LineSocialProvider(Connection<?> connection) {
		this.providerUserId = connection.getKey().getProviderUserId();
		this.line = (Line) connection.getApi();
	}

	@Override
	public void sendMessage(String message) {
		var textMessage = new TextMessageMixin("text", message);
		var pushMessage = new PushMessageMixin(providerUserId, textMessage);
		line.messagesOperations().pushMessage(pushMessage);
	}
}