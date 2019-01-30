package com.genius.backend.infrastructure.security.social.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.social.autoconfigure.SocialProperties;

import static com.genius.backend.application.util.CipherUtil.hmacDigest;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.social.facebook")
public class FacebookProperties extends SocialProperties {
	private String appNamespace;
	private String verifyToken;
	private String scope;
	private FacebookProperties.Page page;

	@Getter
	@Setter
	public static class Page {
		private String id;
		private String name;
		private String accessToken;
	}

	public String getAppSecretProof() {
		return hmacDigest(getPage().getAccessToken(), getAppSecret(), "HmacSHA256");
	}
}