package org.springframework.social.kakao.api.impl.json;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ObjectTypeMixin {
	TEXT("text");

	private String name;

	ObjectTypeMixin(String type) {
		this.name = type;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}