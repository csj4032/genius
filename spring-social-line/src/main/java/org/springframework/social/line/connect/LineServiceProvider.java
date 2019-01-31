package org.springframework.social.line.connect;

import org.springframework.social.line.api.Line;
import org.springframework.social.line.api.impl.LineTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * @author Genius Choi
 */
public class LineServiceProvider extends AbstractOAuth2ServiceProvider<Line> {

	private String messageAccessToken;

	public LineServiceProvider(String appId, String appSecret, String messageAccessToken) {
		super(new LineOAuth2Template(appId, appSecret, "https://access.line.me/oauth2/v2.1/authorize", "https://api.line.me/oauth2/v2.1/token"));
		//super(getOAuth2Template(appId, appSecret));
		this.messageAccessToken = messageAccessToken;
	}

	private static OAuth2Template getOAuth2Template(String appId, String appSecret) {
		OAuth2Template oAuth2Template = new OAuth2Template(appId, appSecret, "https://access.line.me/oauth2/v2.1/authorize", "https://api.line.me/oauth2/v2.1/token");
		oAuth2Template.setUseParametersForClientAuthentication(true);
		return oAuth2Template;
	}

	@Override
	public Line getApi(String accessToken) {
		return new LineTemplate(accessToken, messageAccessToken);
	}
}
