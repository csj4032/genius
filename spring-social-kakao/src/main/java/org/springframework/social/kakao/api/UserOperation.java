package org.springframework.social.kakao.api;

public interface UserOperation {

	String getProfileId();

	String getNickname();

	KakaoProfile getUserProfile(String[] profiles);

	KakaoProfile getUserProfile();

	KakaoProfile unlink();

	AccessTokenInfo accessTokenInfo();

	KakaoProfile updateProfile(String profileJsonString);

	KakaoProfile logout();

	KakaoProfile signup(String profileJsonString);

	KakaoIds ids();

	KakaoIds ids(String limit, String fromId, String order);

	KakaoProfile getUserProfile(String userId);

}