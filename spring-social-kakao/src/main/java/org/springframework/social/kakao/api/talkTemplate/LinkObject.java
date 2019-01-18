package org.springframework.social.kakao.api.talkTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LinkObject {
	@JsonProperty("web_url")
	private String webUrl;
	@JsonProperty("mobile_web_url")
	private String mobileWebUrl;
	@JsonProperty("android_execution_params")
	private String androidExecutionParams;
	@JsonProperty("ios_execution_params")
	private String iosExecutionParams;
}
