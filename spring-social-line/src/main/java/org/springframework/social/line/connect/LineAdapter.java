package org.springframework.social.line.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.line.api.Line;
import org.springframework.social.line.api.LineProfile;

/**
 * @author Genius Choi
 */
public class LineAdapter implements ApiAdapter<Line> {

	@Override
	public boolean test(Line line) {
		try {
			line.userOperation().getUserProfile();
			return true;
		} catch (ApiException e) {
			return false;
		}
	}

	@Override
	public void setConnectionValues(Line line, ConnectionValues values) {
		LineProfile profile = line.userOperation().getUserProfile();
		values.setProviderUserId(profile.getUserId());
		values.setDisplayName(profile.getDisplayName());
		values.setProfileUrl(profile.getStatusMessage());
		values.setImageUrl(profile.getPictureUrl());
	}

	@Override
	public UserProfile fetchUserProfile(Line api) {
		return null;
	}

	@Override
	public void updateStatus(Line api, String message) {

	}
}
