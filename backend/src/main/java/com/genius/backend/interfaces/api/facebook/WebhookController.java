package com.genius.backend.interfaces.api.facebook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

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
	public void webhook(HttpServletRequest httpServletRequest) {
		StringBuffer jb = new StringBuffer();
		String line = null;
		try (BufferedReader reader = httpServletRequest.getReader();) {
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("receivedMessage : {}", jb.toString());
	}
}