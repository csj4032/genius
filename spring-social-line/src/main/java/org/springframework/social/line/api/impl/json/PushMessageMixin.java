package org.springframework.social.line.api.impl.json;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.social.line.api.Message;

import java.util.Collections;
import java.util.List;

@Value
@AllArgsConstructor
public class PushMessageMixin {
	private String to;
	private List<Message> messages;

	public PushMessageMixin(String to, Message message) {
		this.to = to;
		this.messages = Collections.singletonList(message);
	}
}