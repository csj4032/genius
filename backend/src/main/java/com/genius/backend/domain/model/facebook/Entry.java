package com.genius.backend.domain.model.facebook;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entry implements Serializable {
	public String id;
	public Long time;
	public List<Messaging> messaging;

	@JsonCreator
	public Entry(
			@JsonProperty("code") String id,
			@JsonProperty("time") Long time,
			@JsonProperty("messaging") List<Messaging> messaging) {
		this.id = id;
		this.time = time;
		this.messaging = messaging;
	}

	public Entry() {
	}
}