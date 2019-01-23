package org.springframework.social.line.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
public class MessageResponse {

	private final String message;
	private final List<String> details;

	public MessageResponse(@JsonProperty("message") String message, @JsonProperty("details") List<String> details) {
		this.message = message;
		this.details = details == null ? Collections.emptyList() : details;
	}
}
