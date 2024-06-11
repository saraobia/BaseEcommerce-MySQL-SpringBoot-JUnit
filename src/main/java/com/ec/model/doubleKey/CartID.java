package com.ec.model.doubleKey;

import java.io.Serializable;
import java.util.Objects;

public class CartID implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idClient;
	private Integer cartProgr;
	private String idArticle;
	
	
	
	public CartID() {
		super();
	
	}
	
	
	public CartID(String idClient, Integer cartProgr, String idArticle) {
		super();
		this.idClient = idClient;
		this.cartProgr = cartProgr;
		this.idArticle = idArticle;
	}



	@Override
	public int hashCode() {
		return Objects.hash(cartProgr, idArticle, idClient);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartID other = (CartID) obj;
		return Objects.equals(cartProgr, other.cartProgr) && Objects.equals(idArticle, other.idArticle)
				&& Objects.equals(idClient, other.idClient);
	}
	
	

}
