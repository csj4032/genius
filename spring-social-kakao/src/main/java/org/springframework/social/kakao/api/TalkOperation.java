package org.springframework.social.kakao.api;

import org.springframework.social.kakao.api.talkTemplate.TalkObject;

public interface TalkOperation {

	KakaoTalkProfile getUserProfile();

	ResultCode sendTalk(TalkObject talkObject);

}