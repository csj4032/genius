package com.genius.backend.application.exception;

public class UnknownEnumValueException extends RuntimeException {

	public UnknownEnumValueException(String message) {
		super(message);
	}
}