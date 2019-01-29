package com.genius.backend.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class GeniusController {

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = {"/", "/greeting"})
	public String greeting(ModelMap modelMap) {
		modelMap.addAttribute("model", "123");
		return "greeting";
	}
}