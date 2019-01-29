package com.genius.backend.domain.model.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Payload;

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
