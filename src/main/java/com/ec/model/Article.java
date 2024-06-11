package com.ec.model;

import java.io.Serializable;
import java.util.Objects;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "article")
public class Article implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	private String idArticle;
	
	@Column
	private String name;
	
	@Column
	private String descr;
	
	@Column
	private double price;
	
	@Column
	private int qtaAvailable;



	public String getIdArticle() {
		return idArticle;
	}


	public void setIdArticle(String idArticle) {
		this.idArticle = idArticle;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescr() {
		return descr;
	}


	public void setDescr(String descr) {
		this.descr = descr;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getQtaAvailable() {
		return qtaAvailable;
	}


	public void setQtaAvailable(int qtaAvailable) {
		this.qtaAvailable = qtaAvailable;
	}


	@Override
	public int hashCode() {
		return Objects.hash(descr, idArticle, name, price, qtaAvailable);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		return Objects.equals(descr, other.descr) && Objects.equals(idArticle, other.idArticle)
				&& Objects.equals(name, other.name) && price == other.price
				&& Objects.equals(qtaAvailable, other.qtaAvailable);
	}


	@Override
	public String toString() {
		return "Article [idArticle=" + idArticle + ", name=" + name + ", descr=" + descr + ", price=" + price
				+ ", qtaAvailable=" + qtaAvailable + "]";
	}
	

}
