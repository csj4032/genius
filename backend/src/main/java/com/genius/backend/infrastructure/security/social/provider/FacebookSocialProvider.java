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
public class FacebookSocialProvider implements SocialProvider<Facebook> {

	private static String idsForPagesUrl = "https://graph.facebook.com/v3.2/{pId}?fields=ids_for_pages&access_token={accessToken}&appsecret_proof={appSecretProof}";
	private static String messagesUrl = "https://graph.facebook.com/v3.2/me/messages?access_token=";
	private Connection<Facebook> connection;
	private FacebookProperties facebookProperties;

	public FacebookSocialProvider(final Connection<Facebook> connection, final FacebookProperties facebookProperties) {
		this.connection = connection;
		this.facebookProperties = facebookProperties;
	}

	@Override
	public User getUser() {
		var profile = connection.fetchUserProfile();
		var user = getUser(connection);
		user.setEmail(profile.getEmail());
		user.getUserSocial().setPageUserId(getPageUserId(user.getProviderUserId()));
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
	public void pushMessage(final String text) {
		var pageUserId = getPageUserId(connection.getKey().getProviderUserId());
		var mUrl = messagesUrl + facebookProperties.getPage().getAccessToken();
		new RestTemplate().postForObject(mUrl, RequestMessage.builder().recipient(new Recipient(pageUserId)).message(Message.builder().text(text).build()).build(), String.class);
	}

	@Override
	public String getRefreshAccessToken() {
		return null;
	}

	@Override
	public String getAccessToken() {
		return this.connection.createData().getAccessToken();
	}

	@Override
	public Connection<Facebook> getConnection() {
		return connection;
	}

	private String getPageUserId(final String providerUserId) {
		var idsForPagesWrapper = new RestTemplate().getForObject(idsForPagesUrl, IdsForPagesResponse.class, providerUserId, facebookProperties.getPage().getAccessToken(), facebookProperties.getAppSecretProof());
		if (idsForPagesWrapper.getIdsForPages() != null) {
			return idsForPagesWrapper.getIdsForPages().getData().get(0).getId();
		}
		return null;
	}
}