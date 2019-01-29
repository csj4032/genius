package com.genius.backend.domain.model.log;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageLog implements LogElement {

	private long userId;
	private String subject;
	private String message;
	private String cronExpression;

	@Override
	public String toJson(LogJsonValue jsonValueLog) {
		return jsonValueLog.getJsonValue(this);
	}
}