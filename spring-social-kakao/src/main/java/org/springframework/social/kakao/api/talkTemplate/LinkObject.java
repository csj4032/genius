package org.springframework.social.kakao.api.talkTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LinkObject {
	@JsonProperty("web_url")
	private String webUrl;
	@JsonProperty("mobile_web_url")
	private String mobileWebUrl;
}
