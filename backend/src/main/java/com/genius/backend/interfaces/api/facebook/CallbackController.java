package com.genius.backend.interfaces.api.facebook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class CallbackController {

	@Value("${spring.social.facebook.verifyToken}")
	private String facebookVerifyToken;

	@GetMapping("/facebook/webhook")
	public String callBack(@RequestParam(value = "hub.verify_token", required=false) String verifyToken, @RequestParam(value = "hub.challenge", required = false) String challenge) {
		if (verifyToken.equals(facebookVerifyToken)) {
			return challenge;
		} else {
			return "Error, wrong validation token";
		}
	}
}