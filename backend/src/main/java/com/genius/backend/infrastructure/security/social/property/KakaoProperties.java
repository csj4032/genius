package com.genius.backend.infrastructure.security.social.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.social.autoconfigure.SocialProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.social.kakao")
public class KakaoProperties extends SocialProperties {

	private String scope;
}