package org.springframework.social.line.api.impl;

import org.springframework.social.line.api.Line;
import org.springframework.social.line.api.UserOperations;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

/**
 * @author Genius Choi
 */
public class LineTemplate extends AbstractOAuth2ApiBinding implements Line {

	private String accessToken;
	private UserOperations userOperation;

	public LineTemplate(String accessToken) {
		super(accessToken);
		this.accessToken = accessToken;
		initSubApis();
	}

	@Override
	public UserOperations userOperation() {
		return this.userOperation;
	}

	@Override
	public String getAccessToken() {
		return accessToken;
	}

	private void initSubApis() {
		this.userOperation = new UserTemplate(getRestTemplate(), isAuthorized());
	}
}