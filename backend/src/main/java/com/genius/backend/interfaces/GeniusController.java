package com.genius.backend.interfaces;

import com.genius.backend.domain.model.alimy.AlimyDto;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class GeniusController {

	@Value("${spring.application.url}")
	private String applicationUrl;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = {"/", "/main"})
	public String main(ModelMap modelMap) {
		var user = (GeniusSocialUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		modelMap.addAttribute("user", user.getUser());
		modelMap.addAttribute("alimies", List.of(new AlimyDto.Response(), new AlimyDto.Response(), new AlimyDto.Response(), new AlimyDto.Response()));
		modelMap.addAttribute("applicationUrl", applicationUrl);
		return "main";
	}
}