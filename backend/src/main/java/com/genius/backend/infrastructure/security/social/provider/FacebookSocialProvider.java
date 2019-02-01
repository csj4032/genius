package com.genius.backend.infrastructure.security.social.provider;

import com.genius.backend.domain.model.facebook.IdsForPagesResponse;
import com.genius.backend.domain.model.facebook.Message;
import com.genius.backend.domain.model.facebook.Recipient;
import com.genius.backend.domain.model.facebook.RequestMessage;
import com.genius.backend.domain.model.user.User;
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
	public User getUser() {
		return null;
	}

	@Override
	public String getProviderUserId() {
		return null;
	}

	@Override
	public void pushMessage(String text) {
		var providerUserId = connection.getKey().getProviderUserId();
		var pageAccessToken = facebookProperties.getPage().getAccessToken();
		var appSecretProof = facebookProperties.getAppSecretProof();
		var url = "https://graph.facebook.com/v3.2/{pId}?fields=ids_for_pages&access_token={accessToken}&appsecret_proof={appSecretProof}";
		var idsForPagesWrapper = new RestTemplate().getForObject(url, IdsForPagesResponse.class, providerUserId, pageAccessToken, appSecretProof);
		log.info("idsForPagesWrapper : {}", idsForPagesWrapper);
		if(idsForPagesWrapper.getIdsForPages() != null) {
			var pageUserId = idsForPagesWrapper.getIdsForPages().getData().get(0).getId();
			var mUrl = "https://graph.facebook.com/v3.2/me/messages?access_token=" + pageAccessToken;
			var recipient = new Recipient(pageUserId);
			var message = new Message();
			message.setText(text);
			var result = new RestTemplate().postForObject(mUrl, RequestMessage.builder().recipient(recipient).message(message).build(), String.class);
			log.info("message : {}", result);
		}
	}
}