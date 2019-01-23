package org.springframework.social.kakao.api.impl.json;

import lombok.*;

@Value
public class ButtonMixin {
	private String title;
	private LinkMixin link;
}
