package org.springframework.social.kakao.api.talkTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentObject {
	private String title;
	private String description;
	@JsonProperty("image_url")
	private String imageUrl;
	@JsonProperty("image_width")
	@Builder.Default
	private int imageWidth = 640;
	@JsonProperty("image_height")
	@Builder.Default
	private int imageHeight = 640;
	protected LinkObject link;
}