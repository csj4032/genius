package com.genius.backend.interfaces.kakao.talk;

import com.genius.backend.infrastructure.security.social.GeniusUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.connect.web.ConnectSupport;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.social.kakao.api.ResultCode;
import org.springframework.social.kakao.api.impl.KakaoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;

@Slf4j
@RestController
public class TalkController {

	@PostMapping("/send/me")
	public ResponseEntity<ResultCode> sendMe(String message) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		GeniusUserDetail geniusUserDetail = (GeniusUserDetail) authentication.getDetails();
		KakaoTemplate kakaoTemplate = new KakaoTemplate(geniusUserDetail.getUser().getAccessToken());
		return new ResponseEntity<>(kakaoTemplate.talkOperation().sendTalk(message), HttpStatus.OK);
	}
}