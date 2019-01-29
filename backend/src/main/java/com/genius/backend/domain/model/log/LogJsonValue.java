package com.genius.backend.domain.model.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogJsonValue implements LogValue {

	@Override
	public String getJsonValue(HttpRequestLog httpRequestLog) {
		return getValue(httpRequestLog);
	}

	@Override
	public String getJsonValue(HttpResponseLog httpResponseLog) {
		return getValue(httpResponseLog);
	}

	@Override
	public String getJsonValue(SendMessageLog sendTalkLog) {
		return getValue(sendTalkLog);
	}

	private String getValue(Object value) {
		try {
			return new ObjectMapper().writeValueAsString(value);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
		return null;
	}
}