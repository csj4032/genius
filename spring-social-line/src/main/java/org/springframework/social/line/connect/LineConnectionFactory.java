package org.springframework.social.line.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.line.api.Line;

/**
 * @author Genius Choi
 */
public class LineConnectionFactory extends OAuth2ConnectionFactory<Line> {

	public LineConnectionFactory(String appId) {
		this(appId, "");
	}

	public LineConnectionFactory(String appId, String appSecret) {
		super("line", new LineServiceProvider(appId, appSecret), new LineAdapter());
	}
}