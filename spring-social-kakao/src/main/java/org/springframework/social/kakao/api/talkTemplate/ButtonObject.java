package org.springframework.social.kakao.api.talkTemplate;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ButtonObject {
	private String title;
	private LinkObject link;
}
