package com.genius.backend.infrastructure.security.social.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.social.autoconfigure.SocialProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.social.line")
public class LineProperties extends SocialProperties {

	private String scope;
	private LineProperties.Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getMessageAccessToken() {
		return getMessage().getAccessToken();
	}

	public static class Message {
		private String appId;
		private String appSecret;
		private String accessToken;

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getAppSecret() {
			return appSecret;
		}

		public void setAppSecret(String appSecret) {
			this.appSecret = appSecret;
		}

		public String getAccessToken() {
			return accessToken;
		}

		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
		}
	}
}
