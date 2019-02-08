package org.springframework.social.kakao.api.impl;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.kakao.api.FriendsOperation;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.social.kakao.api.TalkOperation;
import org.springframework.social.kakao.api.UserOperation;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.HttpRequestDecorator;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

public class KakaoTemplate extends AbstractOAuth2ApiBinding implements Kakao {

	private UserOperation userOperation;
	private TalkOperation talkOperation;
	private FriendsOperation friendsOperation;

	private String appKey;
	private String adminKey;
	private String accessToken;
	private String refreshToken;
	private RestTemplate adminRestTemplate;

	public KakaoTemplate() {
		initialize();
	}

	public KakaoTemplate(String accessToken) {
		super(accessToken);
		this.accessToken = accessToken;
		initialize();
	}

	public KakaoTemplate(String accessToken, String adminKey) {
		super(accessToken);
		this.accessToken = accessToken;
		this.adminKey = adminKey;
		initialize();
	}

	public KakaoTemplate(String appKey, String accessToken, String refreshToken) {
		super(accessToken);
		this.appKey = appKey;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		initialize();
	}

	@Override
	public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(requestFactory));
	}

	public UserOperation userOperation() {
		return userOperation;
	}

	public TalkOperation talkOperation() {
		return talkOperation;
	}

	public FriendsOperation friendsOperation() {
		return friendsOperation;
	}

	private void initialize() {
		super.setRequestFactory(ClientHttpRequestFactorySelector.bufferRequests(getRestTemplate().getRequestFactory()));
		adminRestTemplate = new RestTemplate(ClientHttpRequestFactorySelector.getRequestFactory());
		adminRestTemplate.setInterceptors(Arrays.asList(new ClientHttpRequestInterceptor[]{new AdminKeyHeaderOAuth2RequestInterceptor(adminKey)}));
		initSubApis();
	}

	private void initSubApis() {
		userOperation = new UserTemplate(getRestTemplate(), adminRestTemplate, isAuthorized());
		talkOperation = new TalkTemplate(getRestTemplate(), isAuthorized());
		friendsOperation = new FriendsTemplate(getRestTemplate(), isAuthorized());
	}

	/**
	 * admin key 사용하는 api호출 시 사용할 rest template 에 설정할 interceptor
	 */
	class AdminKeyHeaderOAuth2RequestInterceptor implements ClientHttpRequestInterceptor {
		private final String adminKey;

		public AdminKeyHeaderOAuth2RequestInterceptor(String adminKey) {
			this.adminKey = adminKey;
		}

		public ClientHttpResponse intercept(final HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			HttpRequest protectedResourceRequest = new HttpRequestDecorator(request);
			protectedResourceRequest.getHeaders().set("Authorization", "KakaoAK " + adminKey);
			return execution.execute(protectedResourceRequest, body);
		}
	}
}
