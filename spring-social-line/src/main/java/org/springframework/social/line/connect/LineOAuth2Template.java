package org.springframework.social.line.connect;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;

import java.util.Map;

public class LineOAuth2Template extends OAuth2Template {

	private String idToken;

	public LineOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
	}

	@Override
	protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Map<String, Object> response) {
		try {
			DecodedJWT jwt = JWT.decode(response.get("id_token").toString());
			System.out.println(jwt.getHeader());
			System.out.println(jwt.getPayload());
			System.out.println(jwt.getSignature());
			System.out.println(jwt.getSubject());
			System.out.println(jwt.getClaim("email").asString());
		} catch (JWTDecodeException exception){
			//Invalid token
		}
		return new AccessGrant(accessToken, scope, refreshToken, expiresIn);
	}


}