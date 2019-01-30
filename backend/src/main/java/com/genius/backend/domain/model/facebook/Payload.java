package com.genius.backend.domain.model.facebook;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class Payload implements Serializable {
	private String url;
	private Long sticker_id;
	private String templateType;
	private String text;
	private List<Element> elements;
	private List<Button> buttons;

	@JsonCreator
	public Payload(
			@JsonProperty("url") String url,
			@JsonProperty("sticker_id") Long sticker_id,
			@JsonProperty("templateType") String templateType,
			@JsonProperty("text")String text,
			@JsonProperty("elements") List<Element> elements,
			@JsonProperty("buttons") List<Button> buttons) {
		this.url = url;
		this.sticker_id = sticker_id;
		this.templateType = templateType;
		this.text = text;
		this.elements = elements;
		this.buttons = buttons;
	}

	public Payload(){

	}
}
