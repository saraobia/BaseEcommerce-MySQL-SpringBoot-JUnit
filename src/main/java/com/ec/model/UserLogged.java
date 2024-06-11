package com.ec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import com.ec.model.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "userLogged")
public class UserLogged implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	private String idUser;
	
	@Column
	private LocalDate dtLogin;
	
	@Column
	private LocalTime tmLogin;
	
	@Column
	@Enumerated(EnumType.STRING)
	private UserRole role;

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public LocalDate getDtLogin() {
		return dtLogin;
	}

	public void setDtLogin(LocalDate dtLogin) {
		this.dtLogin = dtLogin;
	}

	public LocalTime getTmLogin() {
		return tmLogin;
	}

	public void setTmLogin(LocalTime tmLogin) {
		this.tmLogin = tmLogin;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dtLogin, idUser, role, tmLogin);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserLogged other = (UserLogged) obj;
		return Objects.equals(dtLogin, other.dtLogin) && Objects.equals(idUser, other.idUser) && role == other.role
				&& Objects.equals(tmLogin, other.tmLogin);
	}

	@Override
	public String toString() {
		return "UserLogged [idUser=" + idUser + ", dtLogin=" + dtLogin + ", tmLogin=" + tmLogin + ", role=" + role
				+ "]";
	}
	
	
	

}
