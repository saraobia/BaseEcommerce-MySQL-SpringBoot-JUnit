package com.ec.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ec.exception.ClientAlreadyExistsException;
import com.ec.exception.ClientNotFoundException;
import com.ec.exception.ClientOperationException;
import com.ec.model.Client;
import com.ec.service.interfaces.ClientFunctions;

@RestController
@RequestMapping("/")
public class ControllerClient {
	
	@Autowired
	private ClientFunctions clientFunctions;
	
	@PostMapping("client")
	public ResponseEntity<Void> addAClient(@RequestBody Client client) throws ClientAlreadyExistsException, ClientOperationException {
	    boolean addClientSuccesfully = clientFunctions.addClient(client);
		return addClientSuccesfully 
		        ? new ResponseEntity<>(HttpStatus.CREATED)
		        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	

	@GetMapping("client/{idClient}")
	public ResponseEntity<Optional<Client>> getClient(@PathVariable String idClient) throws ClientNotFoundException {
		
		Optional<Client> client = clientFunctions.getClient(idClient);
		
		return !client.isEmpty()
				? new ResponseEntity<>(client, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("client/{idClient}")
	public ResponseEntity<Void> deleteClient(@PathVariable String idClient) throws ClientNotFoundException {
		
		boolean deleteClientSuccesfully = clientFunctions.deleteClient(idClient);
		
		return deleteClientSuccesfully
				? new ResponseEntity<>(HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("client/state")
	public ResponseEntity<Void> updateClient(@RequestBody Client client) throws ClientNotFoundException {
		
		boolean updateClientSuccesfully = clientFunctions.updateClient(client);
		
		return updateClientSuccesfully
				? new ResponseEntity<>(HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("clients")
	public ResponseEntity<List<Client>> getAllClient() {
		
		List<Client> clients = clientFunctions.getAllClient();
		
		return !clients.isEmpty()
				? new ResponseEntity<>(clients, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("clients/{dtSignupStart}/{dtSignupEnd}")
	public ResponseEntity<List<Client>> getAllClient(@PathVariable("dtSignupStart") LocalDate dtSignupStart, 
													 @PathVariable("dtSignupEnd") LocalDate dtSignupEnd) {
		
		List<Client> clientsFromDate = clientFunctions.getAllClient(dtSignupStart, dtSignupEnd);
		return !clientsFromDate .isEmpty()
				? new ResponseEntity<>(clientsFromDate , HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("clients/{surnameLike}")
	public ResponseEntity<List<Client>> getAllClient(@PathVariable("surnameLike") String surnameLike) {
		
		List<Client> clientsFromSurname = clientFunctions.getAllClient(surnameLike);
		return !clientsFromSurname.isEmpty()
				? new ResponseEntity<>(clientsFromSurname , HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
