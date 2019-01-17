package org.springframework.social.kakao.api.talkTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialObject {
	@JsonProperty("like_count")
	private int likeCount;
	@JsonProperty("comment_count")
	private int commentCount;
	@JsonProperty("view_count")
	private int viewCount;
	@JsonProperty("subscriber_count")
	private int subscriberCount;
}