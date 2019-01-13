package com.genius.backend.interfaces;

import com.genius.backend.domain.ApiError;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class GeniusErrorController extends AbstractErrorController {

	public GeniusErrorController() {
		super(new DefaultErrorAttributes());
	}

	@GetMapping("/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "errors/403";
			}
		}
		return "errors/error";
	}

	@Override
	public String getErrorPath() {
		return "error";
	}

	@GetMapping("/error/rest")
	public @ResponseBody
	ResponseEntity<ApiError> restErrorHandler(HttpServletRequest request) {
		var status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		var errors = getErrorAttributes(request, false);
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
				return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, errors), HttpStatus.UNAUTHORIZED);
			}
			if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return new ResponseEntity<>(new ApiError(HttpStatus.FORBIDDEN, errors), HttpStatus.FORBIDDEN);
			}
		}
		return new ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "error", "error"), new HttpHeaders(), HttpStatus.OK);
	}
}