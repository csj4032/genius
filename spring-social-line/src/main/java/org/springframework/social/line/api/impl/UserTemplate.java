package org.springframework.social.line.api.impl;

import org.springframework.social.line.api.LineProfile;
import org.springframework.social.line.api.UserOperations;
import org.springframework.web.client.RestTemplate;

/**
 * @author Genius Choi
 */
public class UserTemplate extends AbstractLineOperations implements UserOperations {

	private final RestTemplate restTemplate;

	public UserTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
		this.restTemplate = restTemplate;
	}

	@Override
	public LineProfile getUserProfile() {
		requireUserAuthorization();
		return restTemplate.getForObject(buildUri("/v2/profile"), LineProfile.class);
	}
}