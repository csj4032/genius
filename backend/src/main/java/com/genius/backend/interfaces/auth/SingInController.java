package com.genius.backend.interfaces.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SingInController {

	@GetMapping(value = "/signin")
	public String login() {
		return "signin";
	}
}