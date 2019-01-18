package org.springframework.social.kakao.api.talkTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextObject extends DefaultObject {
	@JsonProperty("object_type")
	@Builder.Default
	private ObjectType objectType = ObjectType.TEXT;
	private String text;
	@Builder.Default
	private LinkObject link = new LinkObject();
	@JsonProperty("button_title")
	@Builder.Default
	private String buttonTitle = "Alimy";
	private ButtonObject[] buttons;
}