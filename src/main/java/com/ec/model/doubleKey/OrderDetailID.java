package com.ec.model.doubleKey;

import java.io.Serializable;
import java.util.Objects;


public class OrderDetailID implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idOrder;
	private String idArticle;
	
	
	public OrderDetailID() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public OrderDetailID(Integer idOrder, String idArticle) {
		super();
		this.idOrder = idOrder;
		this.idArticle = idArticle;
	}


	@Override
	public int hashCode() {
		return Objects.hash(idArticle, idOrder);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetailID other = (OrderDetailID) obj;
		return Objects.equals(idArticle, other.idArticle) && Objects.equals(idOrder, other.idOrder);
	}
	
	

}
