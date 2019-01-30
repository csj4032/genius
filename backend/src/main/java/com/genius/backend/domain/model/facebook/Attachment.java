package com.genius.backend.domain.model.facebook;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Attachment implements Serializable {
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

	@JsonCreator
	public Attachment(
			@JsonProperty("type") Type type,
			@JsonProperty("payload") Payload payload) {
		this.type = type;
		this.payload = payload;
	}

	public Attachment() {

	}
}
