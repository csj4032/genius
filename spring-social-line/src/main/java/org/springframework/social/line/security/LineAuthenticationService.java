package org.springframework.social.line.security;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.line.api.Line;
import org.springframework.social.security.provider.OAuth2AuthenticationService;

public class LineAuthenticationService extends OAuth2AuthenticationService<Line> {

	public LineAuthenticationService(OAuth2ConnectionFactory<Line> connectionFactory) {
		super(connectionFactory);
	}
}
