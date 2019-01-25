package com.genius.backend.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Slf4j
@Controller
public class GeniusController {

	//@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping({"/", "/greeting"})
	public String greeting(ModelMap modelMap) {
		modelMap.addAttribute("model", "");
		var a = SecurityContextHolder.getContext().getAuthentication();
		System.out.print(a.getAuthorities());
		return "greeting";
	}
}