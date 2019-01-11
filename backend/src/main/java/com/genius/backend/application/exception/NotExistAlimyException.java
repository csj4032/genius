package com.genius.backend.application.exception;

import javax.validation.constraints.NotNull;

public class NotExistAlimyException extends RuntimeException {

	public NotExistAlimyException(@NotNull long id) {
		super("not exist alimy for id " + id);
	}
}