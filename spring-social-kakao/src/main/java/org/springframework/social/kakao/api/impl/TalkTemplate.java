package org.springframework.social.kakao.api.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.kakao.api.KakaoTalkProfile;
import org.springframework.social.kakao.api.ResultCode;
import org.springframework.social.kakao.api.TalkOperation;
import org.springframework.social.kakao.api.talkTemplate.TalkObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class TalkTemplate extends AbstractKakaoOperations implements TalkOperation {

	static final String TALK_SEND_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
	private final RestTemplate restTemplate;
	private final String accessToken;

	public TalkTemplate(RestTemplate restTemplate, String accessToken, boolean isAuthorized) {
		super(isAuthorized);
		this.restTemplate = restTemplate;
		this.accessToken = accessToken;
	}

	@Override
	public KakaoTalkProfile getUserProfile() {
		requireAuthorization();
		return restTemplate.getForObject(buildApiUri("/v1/api/talk/profile"), KakaoTalkProfile.class);
	}

	@Override
	public ResultCode send(TalkObject talkObject) {
		return onSend(talkObject.toJsonMessage());
	}

	private ResultCode onSend(String messageObject) {
		MultiValueMap<String, Object> templateObject = new LinkedMultiValueMap<>();
		templateObject.set("template_object", messageObject);
		HttpHeaders headers = new HttpHeaders();
		try {
			return restTemplate.postForObject(TALK_SEND_URL, new HttpEntity<>(templateObject, headers), ResultCode.class);
		} catch (RestClientException e) {
			log.error("{}", e.getMessage());
			return new ResultCode(1);
		}
	}
}