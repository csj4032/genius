package org.springframework.social.kakao.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoProfileProperties {
	private String nickname;
	private String profile_image;
	private String thumbnail_image;
}
