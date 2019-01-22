package org.springframework.social.line.api.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.line.api.MessagesOperations;
import org.springframework.web.client.RestTemplate;

public class MessagesTemplate extends AbstractLineOperations implements MessagesOperations {

	private final RestTemplate restTemplate;

	public MessagesTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
		this.restTemplate = restTemplate;
	}

	@Override
	public String sendPushMessage(String message) {
		requireUserAuthorization();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(message, headers);
		return restTemplate.postForObject(buildUri("/bot/message/push"), entity, String.class);
	}
}