package com.example.jwt.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class APIException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public APIException(String message) {
		super(message);
		this.message = message;
	}
}
