package com.ec.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ec.exception.UserGenericException;
import com.ec.exception.UserNotFoundException;
import com.ec.model.Client;
import com.ec.model.UserLogged;
import com.ec.model.enums.StatusLogging;
import com.ec.model.enums.StatusRegistration;
import com.ec.model.enums.UserRole;
import com.ec.repository.ClientRepository;
import com.ec.repository.UserRepository;
import com.ec.service.interfaces.SystemFunctions;

@Service
public class ServiceSystem implements SystemFunctions {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Override
	public StatusLogging login(String idClient, String password, UserRole role) throws UserGenericException {
		try {
			Optional<Client> existingClient = clientRepository.findById(idClient);
			if (!existingClient.isPresent()) {
				return StatusLogging.LOGIN_FAILED;
			}
			Client client = existingClient.get();

			if (!client.getIdClient().equals(idClient)) {
				return StatusLogging.WRONG_ID_CLIENT;
			}

			if (!client.getPassword().equals(password)) {
				return StatusLogging.WRONG_PASSWORD;
			}

			
			client.setDtLastLogin(LocalDate.now());
			client.setTmLastLogin(LocalTime.now());
			clientRepository.save(client);

			
			UserLogged userLogged = new UserLogged();
			userLogged.setIdUser(client.getIdClient());
			userLogged.setDtLogin(LocalDate.now());
			userLogged.setTmLogin(LocalTime.now());
			userLogged.setRole(role);
			
			userRepository.save(userLogged);
		} catch (Exception e) {
			throw new UserGenericException("Error occurred while attempting to log in the user. A generic problem occurred during the execution of the login() method.");
			
		}
		return StatusLogging.LOGIN_SUCCESFULLY;

		
	}
	



	@Override
	public StatusLogging logoff(String idClient) {
		Optional<Client> existingClient = clientRepository.findById(idClient);
		if(existingClient.isPresent()) {
			userRepository.deleteById(idClient);
			return StatusLogging.LOGGED_OFF;
		}
		return StatusLogging.ALREADY_LOGGED_OFF;
		
	}

	@Override
	public StatusRegistration signup(Client client) {
		Optional<Client> existingClient = clientRepository.findById(client.getIdClient());
		if(existingClient.isPresent()) {
			return StatusRegistration.ALREADY_SIGNED_UP;
		}
		
		Client newClient = new Client();
		newClient.setIdClient(client.getIdClient());
		newClient.setAddress(client.getAddress());
		newClient.setDtSignup(LocalDate.now());
		newClient.setMail(client.getMail());
		newClient.setName(client.getName());
		newClient.setPassword(client.getPassword());
		newClient.setSurname(client.getSurname());
		newClient.setTypePayment(client.getTypePayment());
		clientRepository.save(newClient);
		return StatusRegistration.SIGNED_UP_SUCCESFULLY;
	}

	@Override
	public StatusRegistration signoff(String idClient) {
		Optional<Client> existingClient = clientRepository.findById(idClient);
		if(!existingClient.isPresent()) {
			return StatusRegistration.USER_NOT_FOUND;
		}
		userRepository.deleteById(idClient);
		Client exClient = existingClient.get();
		exClient.setDtSignoff(LocalDate.now());
		clientRepository.save(exClient);
		return StatusRegistration.SIGNED_OFF_SUCCESFULLY;
		
	}

	@Override
	public boolean isUserLoggedOn(String idClient) {
		return userRepository.findById(idClient).isPresent();
	}

	@Override
	public Optional<UserLogged> getUserLoggedOn(String idClient) {
		Optional<UserLogged> userLoggedOptional = userRepository.findById(idClient);
		return userLoggedOptional.isPresent() ? userLoggedOptional : Optional.empty();
	}

	@Override
	public boolean deleteUserLoggedOn(String idUserLogged) {
	    Optional<UserLogged> userLoggedToDelete = userRepository.findById(idUserLogged);
	    if(userLoggedToDelete.isEmpty()) {
	        throw new UserNotFoundException("User: " + idUserLogged + " not found");
	    }
	    userRepository.deleteById(idUserLogged);
	    return true;
	}

	@Override
	public List<UserLogged> getAllUsersLoggedOn() {
		return userRepository.findAll();
	}



	
	

}
