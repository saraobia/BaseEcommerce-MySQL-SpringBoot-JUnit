package com.ec.service.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ec.exception.ClientAlreadyExistsException;
import com.ec.exception.ClientNotFoundException;
import com.ec.exception.ClientOperationException;
import com.ec.model.Client;

public interface ClientFunctions {
	boolean addClient(Client client) throws ClientAlreadyExistsException, ClientOperationException;
	Optional<Client> getClient(String idClient) throws ClientNotFoundException ;
	boolean deleteClient(String idClient) throws ClientNotFoundException ;
	boolean updateClient(Client client) throws ClientNotFoundException ;
	List<Client> getAllClient();
	List<Client> getAllClient(LocalDate dtSignupStart, LocalDate dtSignupEnd);
	List<Client> getAllClient(String surnameLike);
	
	
}
