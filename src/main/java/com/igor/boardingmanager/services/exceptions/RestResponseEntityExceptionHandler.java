package com.igor.boardingmanager.services.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import net.minidev.json.JSONObject;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(value = { InvalidEmployeeException.class})
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		JSONObject json = new JSONObject();
		json.put("code",HttpStatus.UNPROCESSABLE_ENTITY.value());
		json.put("error", HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
		json.put("cause", ex.getMessage());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(ex, json, headers ,HttpStatus.UNPROCESSABLE_ENTITY, request);
	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		JSONObject json = new JSONObject();
		json.put("code", status.value());
		json.put("error", "Unprocessable Entity.");
		json.put("cause", ex.getFieldError().getDefaultMessage());
		return handleExceptionInternal(ex, json, headers, status, request);
	}
	@ExceptionHandler(value = { ResourceNotFoundException.class})
	protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		JSONObject json = new JSONObject();
		json.put("code", HttpStatus.NOT_FOUND.value());
		json.put("error",HttpStatus.NOT_FOUND.getReasonPhrase());
		json.put("cause", ex.getMessage());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(ex, json, headers ,HttpStatus.NOT_FOUND, request);
	}
}
