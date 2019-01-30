package com.genius.backend.domain.model.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public enum Action {
	@JsonProperty("action-a")
	ACTION_A,
	@JsonProperty("action-b")
	ACTION_B
}
