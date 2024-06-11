package com.ec.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ec.exception.ClientNotFoundException;
import com.ec.exception.UserGenericException;
import com.ec.model.Client;
import com.ec.model.UserLogged;
import com.ec.model.enums.StatusLogging;
import com.ec.model.enums.StatusRegistration;
import com.ec.model.enums.UserRole;
import com.ec.service.interfaces.ClientFunctions;
import com.ec.service.interfaces.SystemFunctions;
import com.ec.utils.JwtUtils;


@RestController
@RequestMapping("/")
public class ControllerSystem {

	@Autowired
	private SystemFunctions systemFunctions;
	
	@Autowired
	private ClientFunctions clientFunctions;
	
	@Autowired 
	private JwtUtils jwtUtils;

	@PostMapping("/login/{idClient}/{password}/{role}")
	public ResponseEntity<?> login(@PathVariable ("idClient") String idClient, 
									@PathVariable ("password") String password,
									@PathVariable ("role") UserRole role) throws UserGenericException, ClientNotFoundException {
	
		StatusLogging login = systemFunctions.login(idClient, password, role);
		Optional<Client> client = clientFunctions.getClient(idClient);
		if(login == StatusLogging.LOGIN_SUCCESFULLY) { 
			return new ResponseEntity<>(jwtUtils.generateAccessToken(client.get()), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/logoff/{idClient}")
	public ResponseEntity<StatusLogging> logoff(@PathVariable ("idClient") String idClient) {
		
		StatusLogging logoff = systemFunctions.logoff(idClient);
		return new ResponseEntity<>(logoff, HttpStatus.OK);
		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<StatusRegistration> signup(@RequestBody Client client) {
		StatusRegistration signup = systemFunctions.signup(client);
		return signup != null
				? new ResponseEntity<>(signup, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	@PostMapping("/signoff/{idClient}")
	public ResponseEntity<StatusRegistration> signoff(@PathVariable ("idClient") String idClient) {
		StatusRegistration signoff = systemFunctions.signoff(idClient);
		return signoff != null
				? new ResponseEntity<>(signoff , HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping("/{idClient}")
	public ResponseEntity<Void> isUserLoggedOn(@PathVariable ("idClient") String idClient) {
		boolean isLogged = systemFunctions.isUserLoggedOn(idClient);
		return isLogged
				? new ResponseEntity<>(HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping("user/{idClient}")
	public ResponseEntity<Optional<UserLogged>> getUserLoggedOn(@PathVariable ("idClient") String idClient) {
		Optional <UserLogged> userLogged = systemFunctions.getUserLoggedOn(idClient);
		return userLogged.isPresent()
				? new ResponseEntity<>(userLogged , HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	@DeleteMapping("/user/{idClient}")
	public ResponseEntity<Void> deleteUserLoggedOn(@PathVariable ("idClient") String idClient) {
		boolean userLoggedIsDeleted = systemFunctions.deleteUserLoggedOn(idClient);
		return userLoggedIsDeleted
				? new ResponseEntity<>(HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserLogged>> getAllUsersLoggedOn() {
		List <UserLogged> usersLogged = systemFunctions.getAllUsersLoggedOn();
		return !usersLogged.isEmpty()
				? new ResponseEntity<>(usersLogged , HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
	
}
