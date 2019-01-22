package org.springframework.social.line.api;

import org.springframework.social.ApiBinding;

/**
 * @author Genius Choi
 */
public interface Line extends ApiBinding {

	UserOperations userOperation();

	MessagesOperations messagesOperations();

	String getAccessToken();
}