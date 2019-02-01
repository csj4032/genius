package org.springframework.social.line.connect;

import lombok.Getter;
import org.springframework.social.oauth2.AccessGrant;

@Getter
public class LineAccessGrant extends AccessGrant {

	private String idToken;

	public LineAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Object idToken) {
		super(accessToken, scope, refreshToken, expiresIn);
		this.idToken = idToken != null ? idToken.toString() : null;
	}
}