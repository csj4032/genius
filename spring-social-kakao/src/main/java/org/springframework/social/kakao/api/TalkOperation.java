package org.springframework.social.kakao.api;

public interface TalkOperation {

	KakaoTalkProfile getUserProfile();

	ResultCode sendTalk(java.lang.String text);
}