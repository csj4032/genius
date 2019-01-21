package org.springframework.social.kakao.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.social.kakao.api.KakaoProfile;

public class KakaoAdapter implements ApiAdapter<Kakao> {

	@Override
	public boolean test(Kakao kakao) {
		try {
			kakao.userOperation().getUserProfile();
			return true;
		} catch (ApiException e) {
			return false;
		}
	}

	@Override
	public void setConnectionValues(Kakao kakao, ConnectionValues values) {
		KakaoProfile profile = kakao.userOperation().getUserProfile();
		values.setProviderUserId(profile.getId());
		values.setDisplayName(profile.getProperties().getNickname());
		values.setProfileUrl(profile.getProperties().getProfile_image());
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