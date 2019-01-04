package com.genius.backend.interfaces;

import com.genius.backend.application.AuthService;
import com.genius.backend.application.UserService;
import com.genius.backend.domain.model.auth.AuthDto;
import com.genius.backend.infrastructure.security.social.GeniusUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.kakao.api.impl.KakaoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@Autowired
	private UsersConnectionRepository usersConnectionRepository;

	@PostMapping(value = "/auth")
	public AuthDto.Response auth(@RequestBody AuthDto.Request request) {
		log.info("{}", request);
		return authService.auth(request);
	}

	@GetMapping(value = "/unlink")
	public String unlink() {
		var geniusUserDetail = (GeniusUserDetail) SecurityContextHolder.getContext().getAuthentication().getDetails();
		var user = userService.findByProviderUserId(geniusUserDetail.getProviderUserId());
		usersConnectionRepository.createConnectionRepository(geniusUserDetail.getProviderUserId()).removeConnections("kakao");
		userService.deleteByIdForUnlink(user.get());
		new KakaoTemplate(user.get().getAccessToken()).userOperation().unlink();
		return "redirect:/logout";
	}
}
