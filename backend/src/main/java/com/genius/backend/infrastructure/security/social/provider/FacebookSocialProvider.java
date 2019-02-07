package com.genius.backend.infrastructure.security.social.provider;

import com.genius.backend.domain.model.facebook.IdsForPagesResponse;
import com.genius.backend.domain.model.facebook.Message;
import com.genius.backend.domain.model.facebook.Recipient;
import com.genius.backend.domain.model.facebook.RequestMessage;
import com.genius.backend.domain.model.user.User;
import com.genius.backend.infrastructure.security.social.property.FacebookProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class FacebookSocialProvider implements SocialProvider {

	private static String idsForPagesUrl = "https://graph.facebook.com/v3.2/{pId}?fields=ids_for_pages&access_token={accessToken}&appsecret_proof={appSecretProof}";
	private static String messagesUrl = "https://graph.facebook.com/v3.2/me/messages?access_token=";
	private Connection<Facebook> connection;
	private FacebookProperties facebookProperties;

	public FacebookSocialProvider(final Connection<?> connection, final FacebookProperties facebookProperties) {
		this.connection = (Connection<Facebook>) connection;
		this.facebookProperties = facebookProperties;
	}

	@Override
	public User getUser() {
		var profile = connection.fetchUserProfile();
		var user = getUser(connection);
		user.setEmail(profile.getEmail());
		return user;
	}

	@Override
	public String getProviderId() {
		return connection.getKey().getProviderId();
	}

	@Override
	public String getProviderUserId() {
		return connection.getKey().getProviderUserId();
	}

	@Override
	public void pushMessage(String text) {
		var providerUserId = connection.getKey().getProviderUserId();
		var pageAccessToken = facebookProperties.getPage().getAccessToken();
		var appSecretProof = facebookProperties.getAppSecretProof();
		var idsForPagesWrapper = new RestTemplate().getForObject(idsForPagesUrl, IdsForPagesResponse.class, providerUserId, pageAccessToken, appSecretProof);
		log.info("idsForPagesWrapper : {}", idsForPagesWrapper);
		if(idsForPagesWrapper.getIdsForPages() != null) {
			var pageUserId = idsForPagesWrapper.getIdsForPages().getData().get(0).getId();
			var mUrl = messagesUrl + pageAccessToken;
			var result = new RestTemplate().postForObject(mUrl, RequestMessage.builder().recipient(new Recipient(pageUserId)).message(Message.builder().text(text).build()).build(), String.class);
			log.info("message : {}", result);
		}
	}
}