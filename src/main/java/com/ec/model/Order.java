package com.ec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.ec.model.enums.StateOrder;
import com.ec.model.enums.TypePayment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userOrder")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer idOrder;

	@Column
	private String idClient;
	
	@Column
	private String address;
	
	@Column
	@Enumerated(EnumType.STRING)
	private TypePayment typePayment;
	
	@Column
	private LocalDate dtOrder;
	
	@Column
	private double totalOrderPrice;
	
	@Column
	@Enumerated(EnumType.STRING)
	private StateOrder state;

	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}

	public String getIdClient() {
		return idClient;
	}

	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public TypePayment getTypePayment() {
		return typePayment;
	}

	public void setTypePayment(TypePayment typePayment) {
		this.typePayment = typePayment;
	}

	public LocalDate getDtOrder() {
		return dtOrder;
	}

	public void setDtOrder(LocalDate dtOrder) {
		this.dtOrder = dtOrder;
	}

	public double getTotalOrderPrice() {
		return totalOrderPrice;
	}

	public void setTotalOrderPrice(double totalOrderPrice) {
		this.totalOrderPrice = totalOrderPrice;
	}

	public StateOrder getState() {
		return state;
	}

	public void setState(StateOrder state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, dtOrder, idClient, idOrder, state, totalOrderPrice, typePayment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(address, other.address) && Objects.equals(dtOrder, other.dtOrder)
				&& Objects.equals(idClient, other.idClient) && Objects.equals(idOrder, other.idOrder)
				&& state == other.state
				&& Double.doubleToLongBits(totalOrderPrice) == Double.doubleToLongBits(other.totalOrderPrice)
				&& typePayment == other.typePayment;
	}

	@Override
	public String toString() {
		return "Order [idOrder=" + idOrder + ", idClient=" + idClient + ", address=" + address + ", typePayment="
				+ typePayment + ", dtOrder=" + dtOrder + ", totalOrderPrice=" + totalOrderPrice + ", state=" + state
				+ "]";
	}
	
	
	


}
