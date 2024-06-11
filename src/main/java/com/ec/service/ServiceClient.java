package com.ec.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ec.exception.ClientAlreadyExistsException;
import com.ec.exception.ClientNotFoundException;
import com.ec.exception.ClientOperationException;
import com.ec.model.Client;
import com.ec.repository.ClientRepository;
import com.ec.service.interfaces.ClientFunctions;

@Service
public class ServiceClient implements ClientFunctions {
	
	@Autowired
	private ClientRepository clientRepository;

	@Override
	public boolean addClient(Client client) throws ClientAlreadyExistsException, ClientOperationException {
		Optional<Client> existingClient = clientRepository.findById(client.getIdClient());
        if (existingClient.isPresent()) {
            throw new ClientAlreadyExistsException(client.getIdClient());
        }
        try {
            clientRepository.save(client);
            return true;
        } catch (Exception e) {
            throw new ClientOperationException("Failed to add client");
        }
	}

	@Override
	public Optional<Client> getClient(String idClient) throws ClientNotFoundException {
		Optional<Client> client = clientRepository.findById(idClient);
        if (!client.isPresent()) {
            throw new ClientNotFoundException(idClient);
        }
        return client;
	}

	@Override
	public boolean deleteClient(String idClient) throws ClientNotFoundException {
		Optional<Client> existingClient = clientRepository.findById(idClient);
		if (!existingClient.isPresent()) {
			throw new ClientNotFoundException(idClient);
		}
		Client client = existingClient.get();
		clientRepository.delete(client);
		return true;
	}

	@Override
	public boolean updateClient(Client client) throws ClientNotFoundException {
		Optional<Client> existingClient = clientRepository.findById(client.getIdClient());
		if (!existingClient.isPresent()) {
	        throw new ClientNotFoundException(client.getIdClient());
	    }
	    return clientRepository.save(client) != null;
	}

	@Override
	public List<Client> getAllClient() {
		return clientRepository.findAll();
	}

	@Override
	public List<Client> getAllClient(LocalDate dtSignupStart, LocalDate dtSignupEnd) {
		return clientRepository.findAllBySignupDateBetween(dtSignupStart, dtSignupEnd);
	}

	@Override
	public List<Client> getAllClient(String surnameLike) {
		return clientRepository.findByExactSurname(surnameLike);
	}

	
}
