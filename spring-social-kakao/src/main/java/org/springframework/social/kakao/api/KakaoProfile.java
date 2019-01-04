package org.springframework.social.kakao.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoProfile {
	private String id;
	private KakaoProfileProperties properties;
	@JsonProperty(value = "kaccount_email")
	private String kaccountEmail;
	@JsonProperty(value = "kaccount_email_verified")
	private boolean kaccountEmailVerified;
}
