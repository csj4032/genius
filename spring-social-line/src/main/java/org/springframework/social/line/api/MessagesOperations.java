package org.springframework.social.line.api;

import org.springframework.social.line.api.impl.json.PushMessageMixin;

public interface MessagesOperations {

	MessageResponse pushMessage(PushMessageMixin pushMessage);

}
