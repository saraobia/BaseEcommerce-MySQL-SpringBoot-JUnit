package com.ec.exception;

public class ClientOperationException extends Exception {

    private static final long serialVersionUID = 1L;

	public ClientOperationException(String message) {
        super(message);
    }
}
