package com.genius.backend.infrastructure.security.social.provider;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FacebookSocialProvider implements SocialProvider {

	private Facebook facebook;
	protected Connection<?> connection;

	public FacebookSocialProvider(Connection<?> connection) {
		facebook = (Facebook) connection.getApi();
		this.connection = connection;
	}

	@Override
	public void sendMessage(String message) {
		var restTemplate = new RestTemplate();
		var accessToken = connection.createData().getAccessToken();
		var pageAccessToken = "EAAIuZCsnNN5wBAPefZC6ZA0rZC7foQzNA7gW5YVONQLAj6d8ZCpnZBLCkVq2NS5Qx2bZACPmcoTWc9XP30ZBhgir8ZAWMLlTsMyV1NQyC9ciPBN44YEQjScMIdAmwfevzbOaZByHibxh1WpCJpE9dqeYNZB3hmNBiYzUTEtZBfAcejkFwQZDZD";
		var userId = connection.getKey().getProviderUserId();
		var hash = hmacDigest(accessToken, "55966eaf785130d34c4321d4d835f970", "HmacSHA256");
		System.out.println(hash);
		//var result = restTemplate.getForObject("https://graph.facebook.com/" + userId + "/ids_for_pages?access_token=" + accessToken + "&appsecret_proof=" + hash, String.class);
		//System.out.println(result);

		var param =
				"\"recipient\":{" +
				"\"id\":\"weg23ro87gfewblwjef\"" +
				"}," +
				"\"message\":{" +
				"\"text\":\"hello, world!\"" +
				"}";

		restTemplate.postForObject("https://graph.facebook.com/me/messages?access_token=" + pageAccessToken, param, String.class);
	}

	public static String hmacDigest(String msg, String keyString, String algo) {
		String digest = null;
		try {
			SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
			Mac mac = Mac.getInstance(algo);
			mac.init(key);

			byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

			StringBuffer hash = new StringBuffer();
			for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(0xFF & bytes[i]);
				if (hex.length() == 1) {
					hash.append('0');
				}
				hash.append(hex);
			}
			digest = hash.toString();
		} catch (UnsupportedEncodingException e) {
		} catch (InvalidKeyException e) {
		} catch (NoSuchAlgorithmException e) {
		}
		return digest;
	}
}

