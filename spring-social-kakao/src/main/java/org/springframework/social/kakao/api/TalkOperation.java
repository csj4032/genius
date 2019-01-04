package org.springframework.social.kakao.api;

public interface TalkOperation {

	KakaoTalkProfile getUserProfile();

	ResultCode sendTalk(String text);
}