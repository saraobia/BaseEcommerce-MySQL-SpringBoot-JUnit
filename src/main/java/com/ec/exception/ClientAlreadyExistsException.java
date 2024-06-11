package com.ec.exception;

public class ClientAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 1L;

	public ClientAlreadyExistsException(String idClient) {
        super("Client with ID " + idClient + " already exists");
    }
}
