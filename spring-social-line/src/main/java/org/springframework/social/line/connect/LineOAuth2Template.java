package org.springframework.social.line.connect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.social.oauth2.OAuth2Template;

import java.util.Map;

@Slf4j
public class LineOAuth2Template extends OAuth2Template {

	public LineOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
	}

	@Override
	protected LineAccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Map<String, Object> response) {
		return new LineAccessGrant(accessToken, scope, refreshToken, expiresIn, response.get("id_token"));
	}
}