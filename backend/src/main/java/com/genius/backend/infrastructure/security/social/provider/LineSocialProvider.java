package com.genius.backend.infrastructure.security.social.provider;

import com.auth0.jwt.JWT;
import com.genius.backend.domain.model.user.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.line.api.Line;
import org.springframework.social.line.api.impl.json.PushMessageMixin;
import org.springframework.social.line.api.impl.json.TextMessageMixin;

public class LineSocialProvider implements SocialProvider {

	private static String issUrl = "https://access.line.me";
	private Connection<?> connection;
	private String providerUserId;
	private Line line;

	public LineSocialProvider(Connection<?> connection) {
		this.connection = connection;
		this.providerUserId = connection.getKey().getProviderUserId();
		this.line = (Line) connection.getApi();
	}

	@Override
	public User getUser() {
		var user = getUser(connection);
		if (isIdToken(providerUserId)) {
			var jwt = JWT.decode(providerUserId);
			user.setProviderUserId(jwt.getClaim("sub").as(String.class));
			user.setEmail(jwt.getClaim("email").as(String.class));
		}
		return user;
	}

	@Override
	public String getProviderId() {
		return connection.getKey().getProviderId();
	}

	@Override
	public String getProviderUserId() {
		if (isIdToken(providerUserId)) {
			return JWT.decode(providerUserId).getClaim("sub").as(String.class);
		}
		return connection.getKey().getProviderUserId();
	}

	@Override
	public void pushMessage(String message) {
		var textMessage = new TextMessageMixin("text", message);
		var pushMessage = new PushMessageMixin(providerUserId, textMessage);
		line.messagesOperations().pushMessage(pushMessage);
	}

	@Override
	public void getFriends() {

	}

	private boolean isIdToken(String token) {
		var jwt = JWT.decode(token);
		var iss = jwt.getClaim("iss").as(String.class);
		return iss.equals(issUrl);
	}
}