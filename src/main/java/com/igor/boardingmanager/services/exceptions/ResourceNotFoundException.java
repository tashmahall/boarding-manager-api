package com.igor.boardingmanager.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException() {
		super(HttpStatus.NOT_FOUND.getReasonPhrase());
	}
	
	public ResourceNotFoundException(String cause) {
		super(cause);
	}
	
}
