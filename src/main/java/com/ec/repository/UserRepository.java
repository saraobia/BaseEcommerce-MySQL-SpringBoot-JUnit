package com.ec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ec.model.UserLogged;

public interface UserRepository extends JpaRepository<UserLogged, String>  {
	
}
