package com.ec.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.ec.exception.UserGenericException;
import com.ec.model.Client;
import com.ec.model.UserLogged;
import com.ec.model.enums.StatusLogging;
import com.ec.model.enums.StatusRegistration;
import com.ec.model.enums.UserRole;

public interface SystemFunctions {
	StatusLogging login(String idClient, String password, UserRole role) throws UserGenericException;
	StatusLogging logoff(String idClient);
	StatusRegistration signup(Client client);
	StatusRegistration signoff(String idClient);
	boolean isUserLoggedOn(String idClient);
	Optional<UserLogged> getUserLoggedOn(String idClient);
	boolean deleteUserLoggedOn(String idUserLogged);
	List<UserLogged> getAllUsersLoggedOn();

}
