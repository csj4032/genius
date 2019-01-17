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
	public ResultCode sendTalk(TalkObject talkObject) {
		return sendTalk(talkObject.toJsonMessage());
	}

	private ResultCode sendTalk(String messageObject) {
		MultiValueMap<String, Object> templateObject = new LinkedMultiValueMap<>();
		try {
			templateObject.set("template_object", messageObject);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(List.of(MediaType.APPLICATION_FORM_URLENCODED));
			headers.add("Authorization", "Bearer " + accessToken);
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(templateObject, headers);
			return restTemplate.postForObject(TALK_SEND_URL, requestEntity, ResultCode.class);
		} catch (RestClientException e) {
			log.error("{}", e.getMessage());
			return new ResultCode(1);
		}
	}
}