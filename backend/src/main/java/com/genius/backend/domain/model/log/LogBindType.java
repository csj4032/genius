package com.genius.backend.domain.model.log;

import com.genius.backend.application.LogService;
import lombok.Getter;
import org.aspectj.lang.JoinPoint;

import java.util.function.Function;

@Getter
public enum LogBindType {
	JOIN_POINT(e -> e.getKind()),
	PROCEEDING_JOIN_POINT(e -> e.getKind(), "a");

	private Function<JoinPoint, String> function;

	LogBindType(Function<JoinPoint, String> function, String a) {
		this.function = function;
	}

	LogBindType(Function<JoinPoint, String> function) {
		this.function = function;
	}
}