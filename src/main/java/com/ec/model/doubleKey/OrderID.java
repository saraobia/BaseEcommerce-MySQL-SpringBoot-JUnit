package com.ec.model.doubleKey;

import java.io.Serializable;
import java.util.Objects;



public class OrderID implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idOrder;
	private String idClient;
	
	
	public OrderID() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return Objects.hash(idClient, idOrder);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderID other = (OrderID) obj;
		return Objects.equals(idClient, other.idClient) && Objects.equals(idOrder, other.idOrder);
	}
	
	
}
