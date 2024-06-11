package com.ec;

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
import com.ec.model.enums.TypePayment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ClientControllerTest {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String CLIENT_URL = BASE_URL + "/client";
    private static final String CLIENT_STATE_URL = BASE_URL + "/client/state";
    private static final String CLIENTS_URL = BASE_URL + "/clients";

    private final RestTemplate restTemplate = new RestTemplate();
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private <T> ResponseEntity<T> sendRequest(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(url, method, requestEntity, responseType);
    }

    private <T> ResponseEntity<T> sendTestRequest(String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) {
        return testRestTemplate.exchange(url, method, requestEntity, responseType);
    }

    @Test
    void testAddClientIsGood() {
        
        Client client = new Client();
        client.setIdClient("C0015");
        client.setName("Christian");
        client.setSurname("Vaìerdi");
        client.setAddress("Via Roma 10, Torino");
        client.setMail("christi.vaìerdi@gmail.com");
        client.setPassword("p*45gh*!");
        client.setTypePayment(TypePayment.PAY_TRANSFER);
        client.setDtSignup(LocalDate.now());
        client.setDtSignoff(null);
        client.setDtLastLogin(null);
        client.setTmLastLogin(null);
        
        HttpHeaders headers = createHeaders();
        
        HttpEntity<Client> requestEntity = new HttpEntity<>(client, headers);
        
        ResponseEntity<Void> response = sendTestRequest(CLIENT_URL, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Void>() {});       
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testGetClientById() {
        String idClient = "C0001";

        HttpHeaders headers = createHeaders();

        String url = CLIENT_URL + "/" + idClient;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Optional<Client>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Optional<Client>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().isPresent());
    }

    @Test
    void testDeleteClient() {
        String idClient = "C0015";

        HttpHeaders headers = createHeaders();

        String url = CLIENT_URL + "/" + idClient;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.DELETE, requestEntity, new ParameterizedTypeReference<Void>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateClient() {
        Client client = new Client();
        client.setIdClient("C0011");
        client.setName("Marco");
        client.setSurname("Bianchi");
        client.setAddress("Via Milano 20, Roma");
        client.setMail("marco.bianchi@example.com");
        client.setPassword("34fr12)?");
        client.setTypePayment(TypePayment.CREDIT_CARD);
        client.setDtSignup(LocalDate.of(2023, 6, 6));
        client.setDtSignoff(null);
        client.setDtLastLogin(null);
        client.setTmLastLogin(null);
        
        HttpHeaders headers = createHeaders();

        HttpEntity<Client> requestEntity = new HttpEntity<>(client, headers);

        ResponseEntity<Void> responseEntity = sendRequest(CLIENT_STATE_URL, HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Void>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetAllClients() {
        HttpHeaders headers = createHeaders();

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<Client>> responseEntity = sendRequest(CLIENTS_URL, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Client>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<Client> clients = responseEntity.getBody();
        assertNotNull(clients);
        assertFalse(clients.isEmpty());
    }

    @Test
    void testGetAllClientsByDateRange() {
        LocalDate dtSignupStart = LocalDate.of(2023, 1, 1);
        LocalDate dtSignupEnd = LocalDate.of(2023, 4, 1);
        
        HttpHeaders headers = createHeaders();

        String url = CLIENTS_URL + "/" + dtSignupStart + "/" + dtSignupEnd;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<Client>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Client>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    void testGetAllClientsBySurname() {
        
        String surnameLike = "Rossi";
        HttpHeaders headers = createHeaders();

        String url = CLIENTS_URL + "/" + surnameLike;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<Client>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Client>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isEmpty());
    }

}
