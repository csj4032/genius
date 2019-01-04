package org.springframework.social.kakao.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RefreshTokenInfo extends KakaoObject implements Serializable {
	@JsonProperty(value = "access_token")
	private String accessToken;
	@JsonProperty(value = "token_type")
	private String tokenType;
	@JsonProperty(value = "refresh_token")
	private String refreshToken;
	@JsonProperty(value = "expires_in")
	private long expiresIn;
}
