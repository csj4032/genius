package org.springframework.social.kakao.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.kakao.api.impl.json.ButtonMixin;
import org.springframework.social.kakao.api.impl.json.LinkMixin;
import org.springframework.social.kakao.api.impl.json.ObjectTypeMixin;
import org.springframework.social.kakao.api.impl.json.TextMixin;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class KakaoTest {

	// https://kauth.kakao.com/oauth/authorize?client_id=62502fd7b03a315ef6a5a07be8296e7a&redirect_uri=http://localhost:9000/signin/kakao&response_type=code

	static final String ACCESS_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
	static final String APP_KEY = "b84d72468acf74d7d892d55cb8e134ff";
	static final String AUTHORIZE_CODE = "ccRa8SWRdiil4Goc_uACaA4zegTsz8jnj61w1wW8s-NpHI9ElKV-fmqNddCaBV5q5BfhRwopdkgAAAFnl1X7KA";
	static String ACCESS_TOKEN = "FQDEdZlbHPCU8tuWAAeVMPFjjRfumLLHvDlXOQopdeIAAAFoWtKzyA";

	@Test
	@Ignore
	public void initKakao() throws JsonProcessingException {
		var restTemplate = new RestTemplate();

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.set("grant_type", "authorization_code");
		parameters.set("client_id", APP_KEY);
		parameters.set("redirect_uri", "http://localhost:9000/signin/kakao");
		parameters.set("code", AUTHORIZE_CODE);

		Map<String, String> response = restTemplate.postForObject(ACCESS_TOKEN_URL, parameters, Map.class);
		log.info("{}", response);
		log.info(response.get("access_token"));
		log.info(response.get("refresh_token"));
	}

	@Test
	public void sendTalk() throws JsonProcessingException {
		var restTemplate = new RestTemplate();
		var templateObject = new LinkedMultiValueMap<String, Object>();
		var objectMapper = new ObjectMapper();
		var headers = new HttpHeaders();
		var link = new LinkMixin("", "", "", "");
		var buttons = List.of(new ButtonMixin("", null));
		var textObject = new TextMixin(ObjectTypeMixin.TEXT, "Test", link,"Alimy", buttons);
		templateObject.set("template_object", objectMapper.writeValueAsString(textObject));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(List.of(MediaType.APPLICATION_FORM_URLENCODED));
		headers.add("Authorization", "Bearer " + ACCESS_TOKEN);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(templateObject, headers);
		restTemplate.postForEntity(TALK_SEND_URL, requestEntity, String.class);
	}

	@Test
	public void refreshToken() {

	}

	@Test
	public void refreshTokenAndSendTalk() {

	}

	@Test
	@Ignore
	public void unlinkTest() {
		var restTemplate = new RestTemplate();
		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(new MediaType[]{MediaType.ALL}));
		headers.add("Authorization", "Bearer " + "Xx1Q6wtRdlBAEcB33SmigLypWiW4n0h2wlLSfwopdaYAAAFoLIAspA");
		HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);
		restTemplate.postForEntity("https://kapi.kakao.com/v1/user/unlink", requestEntity, String.class);
	}

	static final String TALK_SEND_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
}