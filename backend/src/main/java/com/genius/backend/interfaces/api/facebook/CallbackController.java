package com.genius.backend.interfaces.api.facebook;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class CallbackController {

	@GetMapping("/facebook/callback")
	public String callBack(String message) {
		return "702966801";
	}
}