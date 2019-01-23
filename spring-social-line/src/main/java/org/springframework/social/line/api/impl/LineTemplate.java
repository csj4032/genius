package org.springframework.social.line.api.impl;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.line.api.Line;
import org.springframework.social.line.api.MessagesOperations;
import org.springframework.social.line.api.UserOperations;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.OAuth2Version;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.HttpRequestDecorator;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Genius Choi
 */
public class LineTemplate extends AbstractOAuth2ApiBinding implements Line {

	private String appId;
	private String appSecret;
	private String accessToken;
	private String messageAccessToken;
	private boolean isBotLinked;
	private UserOperations userOperation;
	private MessagesOperations messagesOperations;


	public LineTemplate(String accessToken) {
		this(accessToken, null);
	}

	public LineTemplate(String accessToken, String messageAccessToken) {
		this(accessToken, messageAccessToken, null);
	}

	public LineTemplate(String accessToken, String messageAccessToken, String appId) {
		this(accessToken, messageAccessToken, appId, null);
	}

	public LineTemplate(String accessToken, String messageAccessToken, String appId, String appSecret) {
		super(accessToken);
		this.accessToken = accessToken;
		this.messageAccessToken = messageAccessToken;
		this.appId = appId;
		this.appSecret = appSecret;
		this.isBotLinked = messageAccessToken != null ? true : true;
		initSubApis();
	}

	@Override
	public UserOperations userOperation() {
		return this.userOperation;
	}

	@Override
	public MessagesOperations messagesOperations() {
		return this.messagesOperations;
	}

	@Override
	public String getAccessToken() {
		return accessToken;
	}

	@Override
	public String getMessageAccessToken() {
		return messageAccessToken;
	}

	@Override
	public boolean isBotLinked() {
		return this.isBotLinked;
	}

	private void initSubApis() {
		this.userOperation = new UserTemplate(getRestTemplate(), isAuthorized());
		if (isBotLinked) {
			RestTemplate messagesRestTemplate = new RestTemplate(ClientHttpRequestFactorySelector.getRequestFactory());
			messagesRestTemplate.setInterceptors(Arrays.asList(new ClientHttpRequestInterceptor[]{new MessageAccessTokenHeaderOAuth2RequestInterceptor(messageAccessToken)}));
			this.messagesOperations = new MessagesTemplate(messagesRestTemplate, isAuthorized());
		}
	}

	class MessageAccessTokenHeaderOAuth2RequestInterceptor implements ClientHttpRequestInterceptor {
		private final String messageAccessToken;

		public MessageAccessTokenHeaderOAuth2RequestInterceptor(String messageAccessToken) {
			this.messageAccessToken = messageAccessToken;
		}

		public ClientHttpResponse intercept(final HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			HttpRequest protectedResourceRequest = new HttpRequestDecorator(request);
			protectedResourceRequest.getHeaders().set("Authorization", OAuth2Version.BEARER.getAuthorizationHeaderValue(messageAccessToken));
			return execution.execute(protectedResourceRequest, body);
		}
	}
}