package com.nikhil.orm.exceptions;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException() {
		super("Rsource Not Found !!");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

}
