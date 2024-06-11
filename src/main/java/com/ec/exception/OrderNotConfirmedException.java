package com.ec.exception;

public class OrderNotConfirmedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public OrderNotConfirmedException(int idOrder) {
		super("Order with ID: " + idOrder + " not confirmed");
	}

}
