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
public class Message implements Serializable {

	private String mid;
	private Integer seq;
	private List<Attachment> attachments;
	private String text;

	@JsonCreator
	public Message(
			@JsonProperty("mid") String mid,
			@JsonProperty("seq") Integer seq,
			@JsonProperty("attachments") List<Attachment> attachments,
			@JsonProperty("text") String text) {
		this.mid = mid;
		this.seq = seq;
		this.attachments = attachments;
		this.text = text;
	}

	public Message() {

	}
}