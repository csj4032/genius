package org.springframework.social.kakao.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.social.kakao.api.KakaoProfile;
import org.springframework.social.kakao.api.KakaoProfileProperties;

public class KakaoAdapter implements ApiAdapter<Kakao> {

	@Override
	public boolean test(Kakao api) {
		return false;
	}

	@Override
	public void setConnectionValues(Kakao kakao, ConnectionValues values) {
		KakaoProfile profile = kakao.userOperation().getUserProfile();
		KakaoProfileProperties properties = profile.getProperties();
		if (properties == null) properties = new KakaoProfileProperties();
		values.setProviderUserId(profile.getId());
		values.setDisplayName(profile.getProperties().getNickname());
		values.setProfileUrl("");
		values.setImageUrl(profile.getProperties().getProfile_image());
	}

	@Override
	public UserProfile fetchUserProfile(Kakao kakao) {
		KakaoProfile profile = kakao.userOperation().getUserProfile(new String[]{"kakao_account.email"});
		return new UserProfileBuilder()
				.setName(profile.getProperties().getNickname())
				.setUsername(profile.getProperties().getNickname())
				.setEmail(profile.getKaccountEmail())
				.setId(profile.getId())
				.build();
	}

	@Override
	public void updateStatus(Kakao api, String message) {

	}
}
