package com.genius.backend.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
	private HttpStatus status;
	private Date timestamp;
	private String message;
	private List<String> errors;

	public ApiError(HttpStatus status, Date timestamp, String message, List<String> errors) {
		super();
		this.status = status;
		this.timestamp = timestamp;
		this.message = message;
		this.errors = errors;
	}

	public ApiError(HttpStatus status, Date timestamp, String message, String error) {
		super();
		this.status = status;
		this.timestamp = timestamp;
		this.message = message;
		errors = Arrays.asList(error);
	}

	public ApiError(HttpStatus status, Date timestamp, Map<String, Object> error) {
		super();
		this.status = status;
		this.timestamp = timestamp;
		this.message = error.get("message").toString();
		errors = error.values().stream().map(e -> e.toString()).collect(toList());
	}

	public ApiError(HttpStatus status, Date timestamp, String message) {
		super();
		this.status = status;
		this.timestamp = timestamp;
		this.message = message;
		errors = null;
	}
}