package com.bankdata.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -7045978694674921755L;
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
