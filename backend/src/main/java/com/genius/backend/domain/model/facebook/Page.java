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
public class Page implements Serializable {
	private String name;
	private String id;

	@JsonCreator
	public Page(
			@JsonProperty("name") String name,
			@JsonProperty("id") String id) {
		this.name = name;
		this.id = id;
	}

	public Page(){

	}
}
