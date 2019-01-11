package com.genius.backend.application.exception;

import javax.validation.constraints.NotNull;

public class NotExistUserException extends RuntimeException {

	public NotExistUserException(@NotNull long id) {
		super("not exist user for " + id);
	}
}