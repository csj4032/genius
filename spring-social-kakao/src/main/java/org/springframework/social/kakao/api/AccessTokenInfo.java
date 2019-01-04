package org.springframework.social.kakao.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AccessTokenInfo extends KakaoObject implements Serializable {
	private String id;
	private long expiresInMillis;
	private long appId;

	AccessTokenInfo() {
	}
}
