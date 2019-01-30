package com.genius.backend.domain.model.facebook;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Recipient implements Serializable {
	private String id;

	@JsonCreator
	public Recipient(
			@JsonProperty("id")String id) {
		this.id = id;
	}

	public Recipient() {

	}
}