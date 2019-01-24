package com.genius.backend.infrastructure.security.social.provider;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.client.RestTemplate;

public class FacebookSocialProvider implements SocialProvider {

	private String providerUserId;
	private Facebook facebook;

	public FacebookSocialProvider(Connection<?> connection) {
		facebook = (Facebook) connection.getApi();
		providerUserId = connection.getKey().getProviderUserId();
	}

	@Override
	public void sendMessage(String message) {
		var url = "https://graph.facebook.com/v2.6/me/messages?access_token=EAAIO3JWdvxYBAFezx8ZAbM35IZAhF3qS3pLaZCPmInedV9sukXcrYxwd5mSjjS25QpHEPttGbmJtBBFcK8uY9xE5sgdZAsfruZBwtounknHahvnMQS42hxocGAZAzV0JO0rCqpS3HRgj0ZCvn5pwSGaKCydNaqfRqDZAwZBrKX90O50ZArdgcfFQJK";
		var restTemplate = new RestTemplate();
		restTemplate.postForObject(url, "{" +
				"\"messaging_type\": \"RESPONSE\"," +
				"\"recipient\": {" +
				"\"id\": \"2050188298382828\"" +
				"}," +
				"\"message\": {" +
				"\"text\": \"hello, world!\"" +
				"}" +
				"}", String.class);
	}
}
