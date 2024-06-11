package com.ec.model;

import java.io.Serializable;
import java.util.Objects;

import com.ec.model.doubleKey.CartID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart")
@IdClass(CartID.class)
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	private String idClient;
	
	@Id
	@Column
	private Integer cartProgr;
	
	@Id
	@Column
	private String idArticle;

	@Column
	private int qtaOrdered;

	@Column
	private double unitPrice;

	@Column
	private double totalPrice;

	public String getIdClient() {
		return idClient;
	}

	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}
	
	public Integer getCartProgr() {
		return cartProgr;
	}

	public void setCartProgr(Integer cartProgr) {
		this.cartProgr = cartProgr;
	}

	public String getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(String idArticle) {
		this.idArticle = idArticle;
	}

	public int getQtaOrdered() {
		return qtaOrdered;
	}

	public void setQtaOrdered(int qtaOrdered) {
		this.qtaOrdered = qtaOrdered;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public double setTotalPrice(double totalPrice) {
		return this.totalPrice = totalPrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idArticle, idClient, qtaOrdered, totalPrice, unitPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		return Objects.equals(idArticle, other.idArticle) && Objects.equals(idClient, other.idClient)
				&& qtaOrdered == other.qtaOrdered
				&& Double.doubleToLongBits(totalPrice) == Double.doubleToLongBits(other.totalPrice)
				&& Double.doubleToLongBits(unitPrice) == Double.doubleToLongBits(other.unitPrice);
	}

	@Override
	public String toString() {
		return "Cart [idClient=" + idClient + ", idArticle=" + idArticle + ", qtaOrdered=" + qtaOrdered + ", unitPrice="
				+ unitPrice + ", totalPrice=" + totalPrice + "]";
	}
	
	
	
}
