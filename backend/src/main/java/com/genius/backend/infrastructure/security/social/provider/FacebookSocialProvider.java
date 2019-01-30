package com.genius.backend.infrastructure.security.social.provider;

import com.genius.backend.domain.model.facebook.*;
import com.genius.backend.infrastructure.security.social.property.FacebookProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.Connection;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class FacebookSocialProvider implements SocialProvider {

	private Connection<?> connection;
	private FacebookProperties facebookProperties;

	public FacebookSocialProvider(final Connection<?> connection, final FacebookProperties facebookProperties) {
		this.connection = connection;
		this.facebookProperties = facebookProperties;
	}

	@Override
	public void save() {

	}

	@Override
	public void sendMessage(String text) {
		var providerUserId = connection.getKey().getProviderUserId();
		var pageAccessToken = facebookProperties.getPage().getAccessToken();
		var appSecretProof = facebookProperties.getAppSecretProof();
		var url = "https://graph.facebook.com/v3.2/{pId}?fields=ids_for_pages&access_token={accessToken}&appsecret_proof={appSecretProof}";
		var idsForPagesWrapper = new RestTemplate().getForObject(url, IdsForPagesResponse.class, providerUserId, pageAccessToken, appSecretProof);
		log.info("idsForPagesWrapper : {}", idsForPagesWrapper);
		var pageUserId = idsForPagesWrapper.getIdsForPages().getData().get(0).getId();
		var mUrl = "https://graph.facebook.com/v3.2/me/messages?access_token=" + pageAccessToken;
		var recipient = Recipient.builder().id(pageUserId).build();
		var message = Message.builder().text(text).build();
		var result = new RestTemplate().postForObject(mUrl, RequestMessage.builder().recipient(recipient).message(message).build(), String.class);
		log.info("message : {}", result);
	}
}