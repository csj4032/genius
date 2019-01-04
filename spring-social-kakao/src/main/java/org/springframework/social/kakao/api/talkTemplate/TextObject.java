package org.springframework.social.kakao.api.talkTemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextObject extends DefaultObject {
	private String text;
	private LinkObject link;
	@JsonProperty("button_title")
	private String buttonTitle;
	private ButtonObject[] buttons;
}