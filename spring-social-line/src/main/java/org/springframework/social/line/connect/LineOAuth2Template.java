package org.springframework.social.line.connect;

import org.springframework.social.oauth2.OAuth2Template;

public class LineOAuth2Template extends OAuth2Template {

	public LineOAuth2Template(String clientId, String clientSecret) {
		super(clientId, clientSecret, "https://access.line.me/oauth2/v2.1/authorize", "https://api.line.me/oauth2/v2.1/token");
		setUseParametersForClientAuthentication(true);
	}
}
