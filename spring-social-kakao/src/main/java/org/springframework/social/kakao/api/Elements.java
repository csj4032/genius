package org.springframework.social.kakao.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Elements {
	private long id;
	@JsonProperty("profile_nickname")
	private String profileNickname;
	@JsonProperty("profile_thumbnail_image")
	private String profileThumbnailImage;
}