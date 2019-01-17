package org.springframework.social.kakao.api.talkTemplate;

import org.springframework.social.kakao.api.ResultCode;
import org.springframework.social.kakao.api.TalkOperation;

public abstract class DefaultObject implements TalkObject {

	@Override
	public ResultCode accept(TalkOperation talkOperation) {
		return talkOperation.sendTalk(this);
	}
}