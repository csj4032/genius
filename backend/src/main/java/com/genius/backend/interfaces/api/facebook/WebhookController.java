package com.genius.backend.interfaces.api.facebook;

import com.genius.backend.domain.model.facebook.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class WebhookController {

	@Value("${spring.social.facebook.verifyToken}")
	private String facebookVerifyToken;

	@GetMapping("/facebook/webhook")
	public String webhook(@RequestParam(value = "hub.verify_token", required = false) String verifyToken, @RequestParam(value = "hub.challenge", required = false) String challenge) {
		if (verifyToken.equals(facebookVerifyToken)) {
			return challenge;
		} else {
			return "Error, wrong validation token";
		}
	}

	@PostMapping("/facebook/webhook")
	public void webhook(@RequestBody ResponseMessage receivedMessage) {
		log.info("receivedMessage : {}", receivedMessage);
	}
}