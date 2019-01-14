package com.genius.backend.interfaces;

import com.genius.backend.domain.ApiError;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping({"${server.error.path:/error}"})
public class GeniusErrorController extends AbstractErrorController {

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
			return modelAndView;
		}
		if (status == HttpStatus.FORBIDDEN) {
			modelAndView.setViewName("/errors/403");
			return modelAndView;
		}
		if (status == HttpStatus.NOT_FOUND) {
			modelAndView.setViewName("/errors/404");
			return modelAndView;
		}
		return modelAndView;
	}

	@RequestMapping
	public ResponseEntity<ApiError> error(HttpServletRequest request) {
		var status = this.getStatus(request);
		var errors = getErrorAttributes(request, false);
		if (status == HttpStatus.UNAUTHORIZED) {
			return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, new Date(), HttpStatus.UNAUTHORIZED.getReasonPhrase()), HttpStatus.UNAUTHORIZED);
		}
		if (status == HttpStatus.FORBIDDEN) {
			return new ResponseEntity<>(new ApiError(HttpStatus.FORBIDDEN, new Date(), HttpStatus.FORBIDDEN.getReasonPhrase()), HttpStatus.FORBIDDEN);
		}
		if (status == HttpStatus.NOT_FOUND) {
			return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, new Date(), HttpStatus.NOT_FOUND.getReasonPhrase()), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, new Date(), errors.get("message").toString(), "error"), new HttpHeaders(), HttpStatus.OK);
	}

	@Override
	public String getErrorPath() {
		return "error";
	}
}