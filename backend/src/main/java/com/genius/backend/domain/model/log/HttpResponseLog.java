package com.genius.backend.domain.model.log;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponseLog implements LogElement {
	private int status;
	private String contentType;
	private String viewName;
	private Map<String, Object> model;

	@Override
	public String toJson(LogJsonValue jsonValueLog) {
		return jsonValueLog.getJsonValue(this);
	}
}