package com.genius.backend.interfaces;

import com.genius.backend.application.AlimyService;
import com.genius.backend.application.ProviderType;
import com.genius.backend.domain.model.NavigationItem;
import com.genius.backend.domain.model.alimy.AlimyDto;
import com.genius.backend.domain.model.user.User;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
	@PostMapping("/")
	public String save(ModelMap modelMap, @Valid AlimyDto.RequestForSaveForm requestForSaveForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			modelMap.addAttribute("requestForSaveForm", requestForSaveForm);
			return "main";
		}
		var request = new ModelMapper().map(requestForSaveForm, AlimyDto.RequestForSave.class);
		request.setUserId(((GeniusSocialUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId());
		alimyService.save(request);
		return "redirect:/";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/")
	public String delete(@RequestParam("alimyId") Long alimyId) {
		alimyService.delete(List.of(alimyId));
		return "redirect:/";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@ModelAttribute
	public void getModelMap(ModelMap modelMap) {
		var user = ((GeniusSocialUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
		var alimies = alimyService.findByUserId(user.getId());
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("alimies", alimies);
		modelMap.addAttribute("applicationUrl", applicationUrl);
		modelMap.addAttribute("navigationItems", getNavigationItems(user, alimies));
	}

	private ArrayList<NavigationItem> getNavigationItems(User user, List<AlimyDto.Response> alimies) {
		var items = new ArrayList<NavigationItem>();
		items.add(NavigationItem.builder().name("About").link("#about").build());
		if (!alimies.isEmpty()) items.add(NavigationItem.builder().name("Schedule").link("#schedule").build());
		items.add(NavigationItem.builder().name("Register").link("#register").build());
		if (user.getProviderType().equals(ProviderType.FACEBOOK)) items.add(NavigationItem.builder().name("ChatBot").link("https://m.me/alimychoibom").build());
		items.add(NavigationItem.builder().name("Logout").link("/logout").build());
		return items;
	}
}