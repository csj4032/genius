package com.genius.backend.infrastructure;

import com.genius.backend.domain.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@ControllerAdvice
public class GeniusResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		List<String> errors = ex.getConstraintViolations().stream().map(e -> getConstraintViolationExceptionMessage(e)).collect(toList());
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, new Date(), ex.getLocalizedMessage(), errors);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getField() + " : " + e.getDefaultMessage()).collect(toList());
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, new Date(), ex.getMessage(), errors);
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, new Date(), ex.getLocalizedMessage(), error), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InsufficientAuthenticationException.class)
	public ResponseEntity<ApiError> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex, WebRequest request) {
		return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, new Date(), ex.getLocalizedMessage(), ex.getMessage()), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	private String getConstraintViolationExceptionMessage(ConstraintViolation<?> e) {
		return e.getRootBeanClass().getName() + " " + e.getPropertyPath() + ": " + e.getMessage();
	}
}