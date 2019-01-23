package org.springframework.social.line.api.impl.json;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Value;
import org.springframework.social.line.api.Message;

@Value
@JsonTypeName("text")
public class TextMessageMixin implements Message {
	private String type;
	private String text;

	public TextMessageMixin(final String type, final String text) {
		this.type = type;
		this.text = text;
	}
}