package com.ec.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ec.model.Client;

public interface ClientRepository extends JpaRepository<Client, String> {
	 @Query("SELECT c FROM Client c WHERE c.dtSignup BETWEEN :dtSignupStart AND :dtSignupEnd")
	  List<Client> findAllBySignupDateBetween(@Param("dtSignupStart") LocalDate dtSignupStart, @Param("dtSignupEnd") LocalDate dtSignupEnd);

	 @Query("SELECT c FROM Client c WHERE c.surname = :surnameLike")
	 List<Client> findByExactSurname(@Param("surnameLike") String surnameLike);

}
