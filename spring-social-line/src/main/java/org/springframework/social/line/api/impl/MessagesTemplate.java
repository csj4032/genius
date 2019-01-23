package org.springframework.social.line.api.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.line.api.MessageResponse;
import org.springframework.social.line.api.MessagesOperations;
import org.springframework.social.line.api.impl.json.PushMessageMixin;
import org.springframework.web.client.RestTemplate;

public class MessagesTemplate extends AbstractLineOperations implements MessagesOperations {

	private final RestTemplate restTemplate;

	public MessagesTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
		this.restTemplate = restTemplate;
	}

	@Override
	public MessageResponse pushMessage(PushMessageMixin pushMessage) {
		requireUserAuthorization();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PushMessageMixin> entity = new HttpEntity<>(pushMessage, headers);
		return restTemplate.postForObject(buildUri("/v2/bot/message/push"), entity, MessageResponse.class);
	}
}