package com.genius.backend.domain.model.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Payload;

@Getter
@Setter
public class Attachment {
	public enum Type {
		@JsonProperty("audio")
		AUDIO,
		@JsonProperty("image")
		IMAGE,
		@JsonProperty("video")
		VIDEO
	}
	public Type type;
	public Payload payload;
}
