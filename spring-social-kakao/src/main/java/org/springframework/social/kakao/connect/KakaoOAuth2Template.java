package org.springframework.social.kakao.connect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.social.oauth2.OAuth2Template;

@Slf4j
public class KakaoOAuth2Template extends OAuth2Template {

	public KakaoOAuth2Template(String clientId, String clientSecret) {
		super(clientId, clientSecret, "https://kauth.kakao.com/oauth/authorize", "https://kauth.kakao.com/oauth/token");
		setUseParametersForClientAuthentication(true);
	}
}