package org.springframework.social.kakao.api.talkTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultObject {
	@JsonProperty("object_type")
	private String objectType;
}