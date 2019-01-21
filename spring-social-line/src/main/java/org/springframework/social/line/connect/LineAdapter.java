package org.springframework.social.line.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.line.api.Line;

/**
 * @author Genius Choi
 */
public class LineAdapter implements ApiAdapter<Line> {

	@Override
	public boolean test(Line api) {
		return false;
	}

	@Override
	public void setConnectionValues(Line api, ConnectionValues values) {

	}

	@Override
	public UserProfile fetchUserProfile(Line api) {
		return null;
	}

	@Override
	public void updateStatus(Line api, String message) {

	}
}
