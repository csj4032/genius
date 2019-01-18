package org.springframework.social.kakao.api.talkTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedObject extends DefaultObject {
	@JsonProperty("object_type")
	@Builder.Default
	private ObjectType objectType = ObjectType.FEED;
	private ContentObject content;
	private SocialObject social;
	private ButtonObject[] buttons = new ButtonObject[]{ButtonObject.builder().title("Alimy").build()};
}