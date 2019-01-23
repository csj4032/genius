package org.springframework.social.kakao.api;

import org.springframework.social.kakao.api.impl.json.TextMixin;

public interface TalkOperation {

	KakaoTalkProfile getUserProfile();

	ResultCode send(TextMixin textMessageMixin);
}