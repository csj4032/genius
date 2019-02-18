package com.genius.backend.interfaces;

import com.genius.backend.application.AlimyService;
import com.genius.backend.application.ProviderType;
import com.genius.backend.domain.model.NavigationItem;
import com.genius.backend.domain.model.alimy.AlimyDto;
import com.genius.backend.domain.model.user.User;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class GeniusController {

	@Value("${spring.application.url}")
	private String applicationUrl;

	@Autowired
	private AlimyService alimyService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/")
	public String main(AlimyDto.RequestForSaveForm requestForSaveForm) {
		return "main";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@ModelAttribute(name = "mainData")
	public void getModelMap(ModelMap modelMap) {
		var user = ((GeniusSocialUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		var alimies = alimyService.findByUserId(user.getId());
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("alimies", alimies);
		modelMap.addAttribute("applicationUrl", applicationUrl);
		modelMap.addAttribute("navigationItems", getNavigationItems(user, alimies));
	}

	private List<NavigationItem> getNavigationItems(User user, List<AlimyDto.Response> alimies) {
		var items = new ArrayList<NavigationItem>();
		items.add(NavigationItem.builder().name("About").link("#about").isScroll(true).build());
		if (!alimies.isEmpty()) items.add(NavigationItem.builder().name("Schedule").link("#schedule").isScroll(true).build());
		items.add(NavigationItem.builder().name("Register").link("#register").isScroll(true).build());
		if (user.getProviderType().equals(ProviderType.FACEBOOK)) items.add(NavigationItem.builder().name("ChatBot").link("https://m.me/alimychoibom").isScroll(false).build());
		items.add(NavigationItem.builder().name("Logout").link("/logout").isScroll(false).build());
		return items;
	}
}