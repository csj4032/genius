package com.genius.backend.domain.model.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogJsonValue implements LogValue {

	public String getJsonValue(HttpRequestLog httpRequestLog) {
		return getValue(httpRequestLog);
	}

	public String getJsonValue(HttpResponseLog httpResponseLog) {
		return getValue(httpResponseLog);
	}

	private String getValue(Object value) {
		try {
			return new ObjectMapper().writeValueAsString(value);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
