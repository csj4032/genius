package com.genius.backend.infrastructure.scheduling;

import com.genius.backend.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.kakao.api.impl.KakaoTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccessKeyRefreshScheduling {

	@Autowired
	private UserRepository userRepository;

	@Value("${spring.social.kakao.clientId}")
	private String clientId;

	@Scheduled(cron = "0 0 0/1 * * ?")
	public void byMinute() {
		log.info("AccessToken Refresh Start");
		userRepository.findAll().stream().forEach(e -> {
			var kakaoTemplate = new KakaoTemplate(clientId, e.getAccessToken(), e.getRefreshToken());
			var refreshTokenInfo = kakaoTemplate.accessTokenOperation().getRefreshTokenInfo();
			e.setAccessToken(refreshTokenInfo.getAccessToken());
			e.setExpiredTime(refreshTokenInfo.getExpiresIn());
			userRepository.save(e);
		});
		log.info("AccessToken Refresh End");
	}
}