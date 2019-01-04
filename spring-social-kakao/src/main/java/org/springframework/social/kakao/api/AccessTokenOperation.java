package org.springframework.social.kakao.api;

public interface AccessTokenOperation {

	AccessTokenInfo getAccessTokenInfo();

	RefreshTokenInfo getRefreshTokenInfo();
}
