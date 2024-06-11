package com.ec.exception;

public class OrderNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(int idOrder) {
		super("Order with ID: " + idOrder + " not found");
	}
	
	

}
