package org.springframework.social.kakao.api;

import org.springframework.social.ApiBinding;

public interface Kakao extends ApiBinding {

	TalkOperation talkOperation();

	UserOperation userOperation();

	AccessTokenOperation accessTokenOperation();
}
