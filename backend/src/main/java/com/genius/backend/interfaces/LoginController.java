package com.genius.backend.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginController {

	@GetMapping(value = "/login")
	public String login() {
		return "login.html";
	}
}