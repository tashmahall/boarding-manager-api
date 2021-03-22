package com.igor.boardingmanager.services.exceptions;

import java.time.format.DateTimeParseException;

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
	@ExceptionHandler(value = { InvalidEmployeeException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		JSONObject json = jsonMethod(HttpStatus.UNPROCESSABLE_ENTITY.value(),HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),ex.getMessage());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(ex, json, headers ,HttpStatus.UNPROCESSABLE_ENTITY, request);
	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		JSONObject json = jsonMethod(status.value(),"Unprocessable Entity.",ex.getFieldError().getDefaultMessage());
		return handleExceptionInternal(ex, json, headers, status, request);
	}
	@ExceptionHandler(value = { ResourceNotFoundException.class})
	protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		JSONObject json = jsonMethod(HttpStatus.NOT_FOUND.ordinal(),HttpStatus.NOT_FOUND.getReasonPhrase(),ex.getLocalizedMessage());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(ex, json, headers ,HttpStatus.NOT_FOUND, request);
	}
	@ExceptionHandler(value = { DateTimeParseException.class})
	protected ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException ex, WebRequest request) {
		JSONObject json = jsonMethod(HttpStatus.BAD_REQUEST.ordinal(),HttpStatus.BAD_REQUEST.getReasonPhrase(),"the query parameter is not a date");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(ex, json, headers ,HttpStatus.BAD_REQUEST, request);
	}
	
	
	private JSONObject jsonMethod(int code, String error, String cause) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("error",error);
		json.put("cause", cause);
		return json;
	}
}
