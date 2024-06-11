package com.ec;


import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ec.model.Client;
import com.ec.model.UserLogged;
import com.ec.model.enums.StatusLogging;
import com.ec.model.enums.StatusRegistration;
import com.ec.model.enums.TypePayment;
import com.ec.model.enums.UserRole;

@SpringBootTest
public class SystemControllerTest {

	private static final String BASE_URL = "http://localhost:8080";
	private static final String USER_URL = BASE_URL + "/user";
	private static final String USERS_URL = BASE_URL + "/users";
	private static final String LOGIN_URL = BASE_URL + "/login";
	private static final String LOGOFF_URL = BASE_URL + "/logoff";
	private static final String SIGNUP_URL = BASE_URL + "/signup";
	private static final String SIGNOFF_URL = BASE_URL + "/signoff";

	private final RestTemplate restTemplate = new RestTemplate();
	private final TestRestTemplate testRestTemplate = new TestRestTemplate();

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	private <T> ResponseEntity<T> sendRequest(String url, HttpMethod method, HttpEntity<?> requestEntity,
			ParameterizedTypeReference<T> responseType) {
		return restTemplate.exchange(url, method, requestEntity, responseType);
	}

	private <T> ResponseEntity<T> sendTestRequest(String url, HttpMethod method, HttpEntity<?> requestEntity,
			ParameterizedTypeReference<T> responseType) {
		return testRestTemplate.exchange(url, method, requestEntity, responseType);
	}

	@Test
	void loginIsGood() {

		String idClient = "C0012";
		String password = "passnew!";
		UserRole role = UserRole.ADMIN;

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = LOGIN_URL + "/" + idClient + "/" + password + "/" + role;

		ResponseEntity<StatusLogging> responseEntity = sendRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<StatusLogging>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		StatusLogging actualStatLogging = responseEntity.getBody();
		assertEquals(actualStatLogging, StatusLogging.LOGIN_SUCCESFULLY);
	}
	
	//TODO: Complete bad methods
	@Test
	void loginIsBad() {

		String idClient = "C099";
		String password = "passnew!";
		UserRole role = UserRole.ADMIN;

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = LOGIN_URL + "/" + idClient + "/" + password + "/" + role;

		ResponseEntity<StatusLogging> responseEntity = sendTestRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<StatusLogging>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		StatusLogging actualStatLogging = responseEntity.getBody();
		assertEquals(actualStatLogging, StatusLogging.CLIENT_NOT_FOUND);
	}

	@Test
	void logoffIsGood() {

		String idClient = "C0005";

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = LOGOFF_URL + "/" + idClient;

		ResponseEntity<StatusLogging> responseEntity = sendRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<StatusLogging>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		StatusLogging actualStatLogging = responseEntity.getBody();
		assertEquals(actualStatLogging, StatusLogging.LOGGED_OFF);
	}

	@Test
	void signupIsGood() {

		Client client = new Client();
		client.setIdClient("C1399");
		client.setAddress("123 Main St");
		client.setMail("new399@example.com");
		client.setName("John");
		client.setPassword("12534*??");
		client.setSurname("Doe");
		client.setTypePayment(TypePayment.CREDIT_CARD);

		HttpHeaders headers = createHeaders();

		HttpEntity<Client> requestEntity = new HttpEntity<>(client, headers);

		ResponseEntity<StatusRegistration> responseEntity = sendRequest(SIGNUP_URL, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<StatusRegistration>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		StatusRegistration actualStatReg = responseEntity.getBody();
		assertEquals(actualStatReg, StatusRegistration.SIGNED_UP_SUCCESFULLY);
	}
	
	@Test
	void signoffIsGood() {

		String idClient = "C0010";

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = SIGNOFF_URL + "/" + idClient;

		ResponseEntity<StatusRegistration> responseEntity = sendRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<StatusRegistration>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		StatusRegistration actualStatLogging = responseEntity.getBody();
		assertEquals(actualStatLogging, StatusRegistration.SIGNED_OFF_SUCCESFULLY);
	}
	
	@Test
	void isUserLoggedIsGood() {

		String idClient = "C0003";

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = BASE_URL + "/" + idClient;

		ResponseEntity<Void> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	
	@Test
	void getUserLoggedIsGood() {

		String idClient = "C0003";

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = USER_URL + "/" + idClient;

		ResponseEntity<Optional<UserLogged>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<Optional<UserLogged>>() {
				});
		
		Optional<UserLogged> actualUserLog = responseEntity.getBody();
		
		
		assertTrue(actualUserLog.isPresent());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	
	@Test
	void deleteUserLoggedIsGood() {

		String idClient = "C0008";

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = USER_URL + "/" + idClient;

		ResponseEntity<Void> responseEntity = sendRequest(url, HttpMethod.DELETE, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	
	@Test
	void getAllUsersLoggedIsGood() {


		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);


		ResponseEntity<List<UserLogged>> responseEntity = sendRequest(USERS_URL, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<UserLogged>>() {
				});
		
		List<UserLogged> allUsers = responseEntity.getBody();
		
		
		assertFalse(allUsers.isEmpty());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	
	
	
	
	
	

}
