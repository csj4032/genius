package com.genius.backend.interfaces.alimy;

import com.genius.backend.application.AlimyService;
import com.genius.backend.domain.model.alimy.AlimyDto;
import com.genius.backend.domain.model.alimy.AlimyStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/alimy")
public class AlimyController {

	@Autowired
	private AlimyService alimyService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{alimyId}")
	public @ResponseBody
	AlimyDto.ResponseForForm alimy(@PathVariable("alimyId") Long alimyId) {
		return alimyService.findByIdForForm(alimyId);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/save")
	public @ResponseBody String save(ModelMap modelMap, @Valid AlimyDto.RequestForSaveForm requestForSaveForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			modelMap.addAttribute("requestForSaveForm", requestForSaveForm);
			return "error";
		}
		alimyService.save(requestForSaveForm);
		return "ok";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/update")
	public @ResponseBody String update(ModelMap modelMap, @Valid AlimyDto.RequestForUpdateForm requestForUpdateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			modelMap.addAttribute("requestForSaveForm", requestForUpdateForm);
			return "error";
		}
		alimyService.update(requestForUpdateForm);
		return "ok";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/delete/{alimyId}")
	public @ResponseBody
	String delete(@PathVariable("alimyId") Long alimyId) {
		//alimyService.delete(List.of(alimyId));
		return "ok";
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/status")
	public @ResponseBody
	AlimyStatus status(@RequestParam("alimyId") Long alimyId) {
		return alimyService.status(alimyId);
	}
}