package com.genius.backend.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.genius.backend.application.AuthService;
import com.genius.backend.application.UserService;
import com.genius.backend.domain.model.auth.AuthDto;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.kakao.api.impl.KakaoTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@PostMapping(value = "/auth")
	public AuthDto.Response auth(@Valid @RequestBody AuthDto.Request request) throws JsonProcessingException {
		return authService.auth(request);
	}

	@GetMapping(value = "/unlink")
	public String unlink() {
		var geniusUserDetail = (GeniusSocialUserDetail) SecurityContextHolder.getContext().getAuthentication().getDetails();
		userService.deleteByIdForUnlink(geniusUserDetail.getUser());
		new KakaoTemplate(geniusUserDetail.getUser().getAccessToken()).userOperation().unlink();
		return "goodbye";
	}
}