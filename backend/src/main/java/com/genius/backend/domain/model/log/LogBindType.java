package com.genius.backend.domain.model.log;

import lombok.Getter;
import org.aspectj.lang.JoinPoint;

import java.util.function.Function;

@Getter
public enum LogBindType {
	JOIN_POINT((e) -> {
		return e.toString();
	}),
	PROCEEDING_JOIN_POINT((e) -> {
		return e.toString();
	});

	private Function<JoinPoint, String> function;

	LogBindType(Function<JoinPoint, String> function) {
		this.function = function;
	}
}