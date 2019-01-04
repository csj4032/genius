package org.springframework.social.kakao.api.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.kakao.api.AccessTokenInfo;
import org.springframework.social.kakao.api.AccessTokenOperation;
import org.springframework.social.kakao.api.RefreshTokenInfo;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class AccessTokenTemplate extends AbstractKakaoOperations implements AccessTokenOperation {
	private final String appKey;
	private final String accessToken;
	private final String refreshToken;
	private final RestTemplate restTemplate;

	public AccessTokenTemplate(RestTemplate restTemplate, String appKey, String accessToken, String refreshToken, boolean isAuthorized) {
		super(isAuthorized);
		this.restTemplate = restTemplate;
		this.appKey = appKey;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	@Override
	public AccessTokenInfo getAccessTokenInfo() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<AccessTokenInfo> responseEntity = restTemplate.exchange(buildApiUri("/v1/user/access_token_info"), HttpMethod.GET, entity, AccessTokenInfo.class);
		return responseEntity.getBody();
	}

	@Override
	public RefreshTokenInfo getRefreshTokenInfo() {
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.set("grant_type", "refresh_token");
		param.set("client_id", appKey);
		param.set("refresh_token", refreshToken);
		return restTemplate.postForObject(buildApiUri("https://kauth.kakao.com","/oauth/token"), param, RefreshTokenInfo.class);
	}
}