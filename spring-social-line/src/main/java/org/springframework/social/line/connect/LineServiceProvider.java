package org.springframework.social.line.connect;

import org.springframework.social.line.api.Line;
import org.springframework.social.line.api.impl.LineTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @author Genius Choi
 */
public class LineServiceProvider extends AbstractOAuth2ServiceProvider<Line> {

	public LineServiceProvider(String appId, String appSecret) {
		super(new LineOAuth2Template(appId, appSecret));
	}

	@Override
	public Line getApi(String accessToken) {
		return new LineTemplate(accessToken);
	}
}
