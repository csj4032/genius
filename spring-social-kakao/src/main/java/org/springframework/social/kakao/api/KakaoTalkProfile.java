package org.springframework.social.kakao.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoTalkProfile {
	private String nickName;
	private String profileImageURL;
	private String thumbnailURL;
	private String countryISO;
}
