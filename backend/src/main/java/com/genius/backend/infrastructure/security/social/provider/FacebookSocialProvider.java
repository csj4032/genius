package com.genius.backend.infrastructure.security.social.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.Connection;
import org.springframework.web.client.RestTemplate;

import static com.genius.backend.application.util.CipherUtil.hmacDigest;

@Slf4j
public class FacebookSocialProvider implements SocialProvider {

	private Connection<?> connection;

	public FacebookSocialProvider(final Connection<?> connection) {
		this.connection = connection;
	}

	@Override
	public void sendMessage(String message) {
		var pId = connection.getKey().getProviderUserId();
		var accessToken = "EAAIuZCsnNN5wBAKn6htkxOneoZCliBMev2JKO6N3nqA9OPbCAkTh4rzpO9YVArm7KQ2Bl2wVtRwmR8qdpSmXet1gZAOJFm3z5u51MBnZBUDeqH4ZB7eNkbkGvLIoZBLLR0SUuhOblx1eN7vkgIKDdAoRj69Xhnwq5Yaf6oayS6Gmz6pcD2ykVl";
		var appSecretProof = hmacDigest(accessToken, "55966eaf785130d34c4321d4d835f970", "HmacSHA256");
		var url = "https://graph.facebook.com/v3.2/{pId}?fields=ids_for_pages&access_token={accessToken}&appsecret_proof={appSecretProof}";
		var result = new RestTemplate().getForObject(url, String.class, pId, accessToken, appSecretProof);
		log.warn("페이스북 메신저 -_-;;, {}", result);
	}
}