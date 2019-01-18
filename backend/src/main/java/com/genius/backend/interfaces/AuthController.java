package com.genius.backend.interfaces;

import com.genius.backend.application.UserService;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.kakao.api.impl.KakaoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/unlink")
	public String unlink() {
		var geniusUserDetail = (GeniusSocialUserDetail) SecurityContextHolder.getContext().getAuthentication().getDetails();
		userService.deleteByIdForUnlink(geniusUserDetail.getUser());
		new KakaoTemplate(geniusUserDetail.getUser().getAccessToken()).userOperation().unlink();
		return "goodbye";
	}
}