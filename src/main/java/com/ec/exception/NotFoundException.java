package com.ec.exception;
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	public NotFoundException(String message) {
		super(message);
	}

}
