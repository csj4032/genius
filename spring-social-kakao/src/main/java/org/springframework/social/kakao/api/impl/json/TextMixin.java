package org.springframework.social.kakao.api.impl.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class TextMixin {
	@JsonProperty("object_type")
	private ObjectTypeMixin objectType;
	private String text;
	private LinkMixin link;
	@JsonProperty("button_title")
	private String buttonTitle;
	private List<ButtonMixin> buttons;

	public TextMixin(ObjectTypeMixin objectType, String text, LinkMixin link, String buttonTitle, List<ButtonMixin> buttons) {
		this.objectType = objectType;
		this.text = text;
		this.link = link;
		this.buttonTitle = buttonTitle;
		this.buttons = buttons;
	}
}
