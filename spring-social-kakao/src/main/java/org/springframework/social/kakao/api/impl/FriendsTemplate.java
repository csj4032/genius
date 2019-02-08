package org.springframework.social.kakao.api.impl;

import org.springframework.social.kakao.api.Friends;
import org.springframework.social.kakao.api.FriendsOperation;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class FriendsTemplate extends AbstractKakaoOperations implements FriendsOperation {

	private final RestTemplate restTemplate;

	public FriendsTemplate(RestTemplate restTemplate, boolean isAuthorized) {
		super(isAuthorized);
		this.restTemplate = restTemplate;
	}

	@Override
	public Friends friends(boolean secureResource, Integer offset, Integer limit, String order) {
		requireAuthorization();
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.set("secure_resource", secureResource == true ? "true" : "false");
		param.set("offset", String.valueOf(offset));
		param.set("limit", String.valueOf(limit));
		return restTemplate.getForObject(buildApiUri("/v1/friends", param), Friends.class);
	}
}