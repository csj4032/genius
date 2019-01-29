package com.genius.backend.domain.model.log;

public interface LogValue {

	String getJsonValue(HttpRequestLog httpRequestLog);

	String getJsonValue(HttpResponseLog httpResponseLog);

	String getJsonValue(SendMessageLog sendTalkLog);

}
