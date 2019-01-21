package org.springframework.social.line.config.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.xml.ApiHelper;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.line.api.Line;

public class LineApiHelper implements ApiHelper<Line> {

	private final static Log logger = LogFactory.getLog(LineApiHelper.class);

	private final UsersConnectionRepository usersConnectionRepository;

	private final UserIdSource userIdSource;

	private LineApiHelper(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
		this.usersConnectionRepository = usersConnectionRepository;
		this.userIdSource = userIdSource;
	}

	@Override
	public Line getApi() {
		if (logger.isDebugEnabled()) {
			logger.debug("Getting API binding instance for LinkedIn");
		}

		Connection<Line> connection = usersConnectionRepository.createConnectionRepository(userIdSource.getUserId()).findPrimaryConnection(Line.class);
		if (logger.isDebugEnabled() && connection == null) {
			logger.debug("No current connection; Returning default LinkedInTemplate instance.");
		}
		return connection != null ? connection.getApi() : null;
	}
}
