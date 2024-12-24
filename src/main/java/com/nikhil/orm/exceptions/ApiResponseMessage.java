package com.nikhil.orm.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseMessage {
	
	private String message;
	
	private boolean success;
	
	private HttpStatus status;

}
