package com.genius.backend.infrastructure.security.social;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.social.autoconfigure.SocialProperties;

import static com.genius.backend.application.util.CipherUtil.hmacDigest;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.social.facebook")
public class FacebookProperties extends SocialProperties {
	private String pageAccessToken;
	private String appNamespace;
	private String returnUrl;
	private String scope;

	public String getAppSecretProof() {
		return hmacDigest(getPageAccessToken(), getAppSecret(), "HmacSHA256");
	}
}
