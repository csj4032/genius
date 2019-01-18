package org.springframework.social.kakao.api.talkTemplate;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ObjectType {
	TEXT("text"),
	FEED("feed");

	private String name;

	ObjectType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
}