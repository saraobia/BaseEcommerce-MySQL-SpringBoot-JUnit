package com.ec.exception;

public class OrderAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public OrderAlreadyExistsException(int idOrder) {
		super("Order with ID: " + idOrder + " already exists");
	}
}
