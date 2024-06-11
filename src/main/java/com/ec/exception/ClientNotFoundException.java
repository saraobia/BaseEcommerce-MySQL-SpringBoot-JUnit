package com.ec.exception;

public class ClientNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

	public ClientNotFoundException(String idClient) {
        super("Client with ID: " + idClient + " not found");
    }
}
