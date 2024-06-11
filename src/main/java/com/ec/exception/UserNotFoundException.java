package com.ec.exception;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public UserNotFoundException(String idClient) {
        super("User with ID: " + idClient + "not found");
    }
}