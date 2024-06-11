package com.ec.model;

import java.io.Serializable;
import java.util.Objects;

import com.ec.model.doubleKey.OrderDetailID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;


@Entity
@Table(name = "orderDetail")
@IdClass(OrderDetailID.class)
public class OrderDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	private Integer idOrder;
	
	@Id
	@Column
	private String idArticle;
	
	@Column
	private int qtaOrdered;
	
	@Column
	private double unitPrice;
	
	@Column
	private double totalPrice;

	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
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

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idArticle, idOrder, qtaOrdered, totalPrice, unitPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderDetail other = (OrderDetail) obj;
		return Objects.equals(idArticle, other.idArticle) && Objects.equals(idOrder, other.idOrder)
				&& qtaOrdered == other.qtaOrdered
				&& Double.doubleToLongBits(totalPrice) == Double.doubleToLongBits(other.totalPrice)
				&& Double.doubleToLongBits(unitPrice) == Double.doubleToLongBits(other.unitPrice);
	}

	@Override
	public String toString() {
		return "OrderDetail [idOrder=" + idOrder + ", idArticle=" + idArticle + ", qtaOrdered=" + qtaOrdered
				+ ", unitPrice=" + unitPrice + ", totalPrice=" + totalPrice + "]";
	}
	
	
	

}
