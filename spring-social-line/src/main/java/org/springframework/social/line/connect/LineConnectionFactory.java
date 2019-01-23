package org.springframework.social.line.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.line.api.Line;

/**
 * @author Genius Choi
 */
public class LineConnectionFactory extends OAuth2ConnectionFactory<Line> {

	public LineConnectionFactory(String appId, String appSecret) {
		this(appId, appSecret, null);
	}

	public LineConnectionFactory(String appId, String appSecret, String messageAccessToken) {
		super("line", new LineServiceProvider(appId, appSecret, messageAccessToken), new LineAdapter());
	}
}