package org.springframework.social.line.api.impl;

import org.springframework.social.line.api.Line;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

/**
 * @author Genius Choi
 */
public class LineTemplate extends AbstractOAuth2ApiBinding implements Line {

	public LineTemplate(String accessToken) {
		super(accessToken);
	}
}