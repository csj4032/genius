package com.genius.backend.application.impl;

import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.model.log.Log;
import com.genius.backend.domain.model.log.LogJsonValue;
import com.genius.backend.domain.model.log.LogType;
import com.genius.backend.domain.model.log.SendTalkLog;
import com.genius.backend.domain.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.social.kakao.api.impl.KakaoTemplate;
import org.springframework.social.kakao.api.talkTemplate.TextObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class KakaoSocailProvider extends AbstractSocialProvider {

	@Autowired
	private LogRepository logRepository;

	@Override
	public void sendAlimy(List<Alimy> alimy) {
		alimy.stream().filter(e -> e.getProviderId().equals("kakao")).forEach(e -> onSendAlimy(e));
	}

	@Retryable(value = {RuntimeException.class}, maxAttempts = 2, backoff = @Backoff(delay = 5000))
	protected void onSendAlimy(Alimy alimy) {
		var talkOperation = new KakaoTemplate(alimy.getUser().getAccessToken()).talkOperation();
		TextObject.builder().text(alimy.getSubject() + "\n" + alimy.getMessage()).build().accept(talkOperation);
		var value = SendTalkLog.builder().subject(alimy.getSubject()).message(alimy.getMessage()).cronExpression(alimy.getCronExpression()).build();
		var gLog = Log.builder().type(LogType.SEND_TALK).value(value.toJson(new LogJsonValue())).build();
		logRepository.save(gLog);
	}
}