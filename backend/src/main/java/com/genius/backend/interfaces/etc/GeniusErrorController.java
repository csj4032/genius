package com.genius.backend.interfaces.etc;

import com.genius.backend.domain.ApiError;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping({"${server.error.path:/error}"})
public class GeniusErrorController extends AbstractErrorController {

	private static final String MESSAGE="message";

	public GeniusErrorController() {
		super(new DefaultErrorAttributes());
	}

	@RequestMapping(produces = {"text/html"})
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		var status = this.getStatus(request);
		var modelAndView = new ModelAndView("errors/error");
		modelAndView.addAllObjects(getErrorAttributes(request, false));
		response.setStatus(status.value());
		if (status == HttpStatus.UNAUTHORIZED) {
			modelAndView.setViewName("/errors/401");
		}
		if (status == HttpStatus.FORBIDDEN) {
			modelAndView.setViewName("/errors/403");
		}
		if (status == HttpStatus.NOT_FOUND) {
			modelAndView.setViewName("/errors/404");
		}
		return modelAndView;
	}

	@RequestMapping
	public ResponseEntity<ApiError> error(HttpServletRequest request) {
		var status = getStatus(request);
		var errors = getErrorAttributes(request, false);
		if (status == HttpStatus.UNAUTHORIZED) {
			return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, new Date(), errors.get(MESSAGE).toString()), HttpStatus.UNAUTHORIZED);
		}
		if (status == HttpStatus.FORBIDDEN) {
			return new ResponseEntity<>(new ApiError(HttpStatus.FORBIDDEN, new Date(), errors.get(MESSAGE).toString()), HttpStatus.FORBIDDEN);
		}
		if (status == HttpStatus.NOT_FOUND) {
			return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, new Date(), errors.get(MESSAGE).toString()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, new Date(), errors.get(MESSAGE).toString()), new HttpHeaders(), HttpStatus.OK);
	}

	@Override
	public String getErrorPath() {
		return "error";
	}
}