package com.genius.backend.interfaces.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SingInController {

	@Value("${spring.application.url}")
	private String applicationUrl;

	@GetMapping(value = "/signin")
	public String login(ModelMap modelMap) {
		modelMap.addAttribute("applicationUrl", applicationUrl);
		return "signin";
	}
}