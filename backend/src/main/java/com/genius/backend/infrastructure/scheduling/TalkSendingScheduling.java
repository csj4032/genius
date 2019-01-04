package com.genius.backend.infrastructure.scheduling;

import com.genius.backend.application.AlimyService;
import com.genius.backend.domain.model.alimy.AlimyStatus;
import com.genius.backend.domain.repository.AlimyRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.kakao.api.impl.KakaoTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@Component
public class TalkSendingScheduling {

	@Autowired
	private AlimyService alimyService;

	@Scheduled(cron = "0 0/1 * * * ?")
	public void byMinute() {
		alimyService.sendTalkForBatch();
	}
}