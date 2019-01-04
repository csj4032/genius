package com.genius.backend.interfaces.kakao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.social.kakao.api.AccessTokenInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class TokenContoller {

	@GetMapping(value = "/token/check")
	public String tokenExpireCheck(String accessToken) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity entity = new HttpEntity(headers);
		ResponseEntity<AccessTokenInfo> responseEntity = restTemplate.exchange("https://kapi.kakao.com/v1/user/access_token_info", HttpMethod.GET, entity, AccessTokenInfo.class);
		log.info("accessToken info : ", responseEntity.getBody());
		return "ok";
	}
}