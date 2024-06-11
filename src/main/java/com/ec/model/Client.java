package com.ec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import com.ec.model.enums.TypePayment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "client")
public class Client implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	private String idClient;
	
	@Column
	private String name;
	
	@Column
	private String surname;
	
	@Column
	private String address;
	
	@Column(unique = true)
	private String mail;
	
	@Column
	private String password;
	
	@Column
	@Enumerated(EnumType.STRING)
	private TypePayment typePayment;
	
	@Column
	private LocalDate dtSignup;
	
	@Column
	private LocalDate dtSignoff;
	
	@Column
	private LocalDate dtLastLogin;
	
	@Column
	private LocalTime tmLastLogin;

	public String getIdClient() {
		return idClient;
	}

	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TypePayment getTypePayment() {
		return typePayment;
	}

	public void setTypePayment(TypePayment typePayment) {
		this.typePayment = typePayment;
	}

	public LocalDate getDtSignup() {
		return dtSignup;
	}

	public void setDtSignup(LocalDate dtSignup) {
		this.dtSignup = dtSignup;
	}

	public LocalDate getDtSignoff() {
		return dtSignoff;
	}

	public void setDtSignoff(LocalDate dtSignoff) {
		this.dtSignoff = dtSignoff;
	}

	public LocalDate getDtLastLogin() {
		return dtLastLogin;
	}

	public void setDtLastLogin(LocalDate dtLastLogin) {
		this.dtLastLogin = dtLastLogin;
	}

	public LocalTime getTmLastLogin() {
		return tmLastLogin;
	}

	public void setTmLastLogin(LocalTime tmLastLogin) {
		this.tmLastLogin = tmLastLogin;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, dtLastLogin, dtSignoff, dtSignup, idClient, mail, name, password, surname,
				tmLastLogin, typePayment);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(address, other.address) && Objects.equals(dtLastLogin, other.dtLastLogin)
				&& Objects.equals(dtSignoff, other.dtSignoff) && Objects.equals(dtSignup, other.dtSignup)
				&& Objects.equals(idClient, other.idClient) && Objects.equals(mail, other.mail)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Objects.equals(surname, other.surname) && Objects.equals(tmLastLogin, other.tmLastLogin)
				&& typePayment == other.typePayment;
	}

	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", name=" + name + ", surname=" + surname + ", address=" + address
				+ ", mail=" + mail + ", password=" + password + ", typePayment=" + typePayment + ", dtSignup="
				+ dtSignup + ", dtSignoff=" + dtSignoff + ", dtLastLogin=" + dtLastLogin + ", tmLastLogin="
				+ tmLastLogin + "]";
	}
	
	

}
