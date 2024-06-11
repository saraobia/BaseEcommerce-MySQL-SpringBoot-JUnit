package com.ec;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ec.model.Article;
import com.ec.model.Order;
import com.ec.model.OrderDetail;
import com.ec.model.enums.StateOrder;
import com.ec.model.enums.TypePayment;

import jakarta.transaction.Transactional;

@SpringBootTest
public class OrderControllerTest {

	private static final String BASE_URL = "http://localhost:8080";
	private static final String ORDER_URL = BASE_URL + "/order";
	private static final String ORDERS_URL = BASE_URL + "/orders";
	private static final String ORDER_ARTICLE_URL = ORDER_URL + "/article";
	private static final String ORDER_ARTICLES_URL = ORDER_URL + "/articles";
	private static final String ORDER_STATE_URL = ORDER_URL + "/state";
	private static final String ORDERS_CLIENT_URL = ORDERS_URL + "/client";
	private static final String ORDERS_ARTICLE_URL = ORDERS_URL + "/article";
	private static final String ORDER_CLOSE_URL = ORDER_STATE_URL + "/close";

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
	void addOrderIsGood() {

		Order newOrder = new Order();
		newOrder.setAddress("Corso Venezia 22, Milano");
		newOrder.setDtOrder(LocalDate.now());
		newOrder.setState(StateOrder.PROGRESS);
		newOrder.setTotalOrderPrice(123.56);
		newOrder.setTypePayment(TypePayment.PAY_TRANSFER);

		String idClient = "C0004";

		HttpHeaders headers = createHeaders();

		HttpEntity<Order> requestEntity = new HttpEntity<>(newOrder, headers);

		String url = ORDER_URL + "/" + idClient;

		ResponseEntity<Order> responseEntity = sendRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Order>() {
				});

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		Order orderAdded = responseEntity.getBody();
		assertNotNull(orderAdded);
	}

	@Test
	void addOrderIsBadForNotFound() {

		Order newOrder = new Order();
		newOrder.setAddress("Corso Venezia 22, Milano");
		newOrder.setDtOrder(LocalDate.now());
		newOrder.setState(StateOrder.PROGRESS);
		newOrder.setTotalOrderPrice(123.56);
		newOrder.setTypePayment(TypePayment.PAY_TRANSFER);

		String idClient = "WrongID";

		HttpHeaders headers = createHeaders();

		HttpEntity<Order> requestEntity = new HttpEntity<>(newOrder, headers);

		String url = ORDER_URL + "/" + idClient;

		ResponseEntity<Order> responseEntity = sendTestRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Order>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		Order orderAdded = responseEntity.getBody();
		assertNotEquals(newOrder, orderAdded);

	}

	@Test
	void addOrderIsBadForOrderAlreadyExist() {

		Order newOrder = new Order();
		newOrder.setIdOrder(1);
		newOrder.setAddress("Corso Venezia 22, Milano");
		newOrder.setDtOrder(LocalDate.now());
		newOrder.setState(StateOrder.PROGRESS);
		newOrder.setTotalOrderPrice(123.56);
		newOrder.setTypePayment(TypePayment.PAY_TRANSFER);

		String idClient = "C0001";

		HttpHeaders headers = createHeaders();

		HttpEntity<Order> requestEntity = new HttpEntity<>(newOrder, headers);

		String url = ORDER_URL + "/" + idClient;

		ResponseEntity<Order> responseEntity = sendTestRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Order>() {
				});

		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		Order orderAdded = responseEntity.getBody();
		assertNotEquals(newOrder, orderAdded);

	}

	@Test
	void getOrderIsGood() {

		int idOrder = 4;

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_URL + "/" + idOrder;

		ResponseEntity<Order> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<Order>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Order order = responseEntity.getBody();
		assertNotNull(order);
	}

	@Test
	void getOrderIsBad() {

		int idOrder = -95;

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_URL + "/" + idOrder;

		ResponseEntity<Order> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<Order>() {
				});

		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		Order order = responseEntity.getBody();
		assertNull(order);
	}

	@Test
	void getAllOrdersIsGood() {

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<List<Order>> responseEntity = sendRequest(ORDERS_URL, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Order>>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<Order> orders = responseEntity.getBody();
		assertNotNull(orders);
	}

	@Test
	void updateOrdersIsGood() {

		Order updatOrder = new Order();
		updatOrder.setIdOrder(1);
		updatOrder.setIdClient("C0003");
		updatOrder.setAddress("new address here");
		updatOrder.setDtOrder(LocalDate.now());
		updatOrder.setState(StateOrder.PROGRESS);
		updatOrder.setTotalOrderPrice(123.56);
		updatOrder.setTypePayment(TypePayment.PAY_TRANSFER);

		int idOrder = 1;

		HttpHeaders headers = createHeaders();

		HttpEntity<Order> requestEntity = new HttpEntity<>(updatOrder, headers);

		String url = ORDER_URL + "/" + idOrder;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.PUT, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	void updateOrdersIsBadForOrderNotFound() {

		Order updatOrder = new Order();
		updatOrder.setIdOrder(1);
		updatOrder.setIdClient("C0003");
		updatOrder.setAddress("new address here");
		updatOrder.setDtOrder(LocalDate.now());
		updatOrder.setState(StateOrder.PROGRESS);
		updatOrder.setTotalOrderPrice(123.56);
		updatOrder.setTypePayment(TypePayment.PAY_TRANSFER);

		int idOrder = 1;

		HttpHeaders headers = createHeaders();

		HttpEntity<Order> requestEntity = new HttpEntity<>(updatOrder, headers);

		String url = ORDER_URL + "/" + idOrder;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.PUT, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	void addArticleToOrderIsGood() {

		Article article = new Article();
		article.setIdArticle("A0001");
		article.setName("Laptop");
		article.setDescr("High-performance laptop");
		article.setPrice(1200.0);
		article.setQtaAvailable(50);

		int idOrder = 1;
		int qtaOrdered = 5;

		HttpHeaders headers = createHeaders();

		HttpEntity<Article> requestEntity = new HttpEntity<>(article, headers);

		String url = ORDER_URL + "/" + idOrder + "/" + qtaOrdered;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	void addArticleToOrderIsBadForArticleNotFound() {

		Article article = new Article();
		article.setIdArticle("A00ds");
		article.setName("Laptop");
		article.setDescr("High-performance laptop");
		article.setPrice(1200.0);
		article.setQtaAvailable(50);

		int idOrder = 1;
		int qtaOrdered = 5;

		HttpHeaders headers = createHeaders();

		HttpEntity<Article> requestEntity = new HttpEntity<>(article, headers);

		String url = ORDER_URL + "/" + idOrder + "/" + qtaOrdered;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}

	@Test
	void addArticleToOrderIsBadForNoContent() {

		Article article = new Article();
		article.setIdArticle("A0001");
		article.setName("Laptop");
		article.setDescr("High-performance laptop");
		article.setPrice(1200.0);
		article.setQtaAvailable(100);

		int idOrder = 1;
		int qtaOrdered = 300;

		HttpHeaders headers = createHeaders();

		HttpEntity<Article> requestEntity = new HttpEntity<>(article, headers);

		String url = ORDER_URL + "/" + idOrder + "/" + qtaOrdered;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

	}

	@Test
	void deleteArticleToOrdersIsGood() {
		
		int idOrder = 5;
		String idArticle = "A0005";

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_URL + "/" + idOrder + "/" + idArticle;

		ResponseEntity<Void> responseEntity = sendRequest(url, HttpMethod.DELETE, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	
	@Test
	void deleteArticleToOrdersIsBad() {
		int idOrder = 95;
		String idArticle = "A0030";

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_URL + "/" + idOrder + "/" + idArticle;

		try {
			ResponseEntity<Void> responseEntity = sendRequest(url, HttpMethod.DELETE, requestEntity,
					new ParameterizedTypeReference<Void>() {
					});

			assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
		}

	}

	@Test
	void deleteAllArticleToOrderIsGood() {
		
		int idOrder = 1;

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_ARTICLES_URL + "/" + idOrder;

		ResponseEntity<Void> responseEntity = sendRequest(url, HttpMethod.DELETE, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	void deleteAllArticleToOrderIsBad() {
		
		int idOrder = 45;

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_ARTICLES_URL + "/" + idOrder;

		try {
			ResponseEntity<Void> responseEntity = sendRequest(url, HttpMethod.DELETE, requestEntity,
					new ParameterizedTypeReference<Void>() {
					});

			assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
		}
		
	}

	@Test
	void confirmOrderIsGood() {

		int idOrder = 1;
		String idClient = "C0003";
		int cartProgr = 1;

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_STATE_URL + "/" + idOrder + "/" + idClient + "/" + cartProgr;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	void confirmOrderIsBadForOrderNotFound() {

		int idOrder = -1;
		String idClient = "C00099";
		int cartProgr = 0;

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_STATE_URL + "/" + idOrder + "/" + idClient + "/" + cartProgr;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}

	@Test
	void confirmOrderIsBadForClientNotFound() {

		int idOrder = 11;
		String idClient = "NoClient";
		int cartProgr = 1;

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_STATE_URL + "/" + idOrder + "/" + idClient + "/" + cartProgr;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}

	@Test
	void getArticleIsGood() {

		int idOrder = 6;
		String idArticle = "A0003";

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_ARTICLE_URL + "/" + idOrder + "/" + idArticle;

		ResponseEntity<Optional<OrderDetail>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<Optional<OrderDetail>>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Optional<OrderDetail> ordersDetail = responseEntity.getBody();
		assertTrue(ordersDetail.isPresent());
		assertNotNull(ordersDetail);

	}

	@Test
	void getArticleIsBad() {

		int idOrder = -1;
		String idArticle = "noArticle";

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_ARTICLES_URL + "/" + idOrder + "/" + idArticle;

		try {
			ResponseEntity<Optional<OrderDetail>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
					new ParameterizedTypeReference<Optional<OrderDetail>>() {
					});

			assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

			Optional<OrderDetail> ordersDetail = responseEntity.getBody();
			assertNull(ordersDetail);
		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
		}
	}

	@Test
	void getAllArticleIsGood() {

		int idOrder = 1;

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_ARTICLES_URL + "/" + idOrder;

		ResponseEntity<List<OrderDetail>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<OrderDetail>>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<OrderDetail> ordersDetail = responseEntity.getBody();
		assertNotNull(ordersDetail);

	}

	@Test
	void getAllArticleIsBad() {
		int idOrder = -1;

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_ARTICLES_URL + "/" + idOrder;

		try {
			ResponseEntity<List<OrderDetail>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
					new ParameterizedTypeReference<List<OrderDetail>>() {
					});

			assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

			List<OrderDetail> ordersDetail = responseEntity.getBody();
			assertNull(ordersDetail);
		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
		}
	}

	@Test
	void getOrdersIdOrderIsGood() {

		String idClient = "C0003";

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDERS_CLIENT_URL + "/" + idClient;

		ResponseEntity<List<Integer>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Integer>>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<Integer> ordersId = responseEntity.getBody();
		assertFalse(ordersId.isEmpty());

	}

	@Test
	void getOrdersIdOrderIsBad() {

		String idClient = "NoClient";

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDERS_CLIENT_URL + "/" + idClient;

		try {
			ResponseEntity<List<Integer>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
					new ParameterizedTypeReference<List<Integer>>() {
					});

			assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

			List<Integer> ordersDetail = responseEntity.getBody();
			assertNull(ordersDetail);
		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
		}
	}

	@Test
	void getOrdersByDateIsGood() {

		String idClient = "C0003";
		LocalDate dateStart = LocalDate.of(2022, 05, 01);
		LocalDate dateEnd = LocalDate.of(2026, 05, 01);

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDERS_CLIENT_URL + "/" + idClient + "/" + dateStart + "/" + dateEnd;

		ResponseEntity<List<Integer>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Integer>>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<Integer> ordersId = responseEntity.getBody();
		assertFalse(ordersId.isEmpty());

	}

	@Test
	void getOrdersByDateIsBad() {

		String idClient = "NoClient";
		LocalDate dateStart = LocalDate.of(2022, 05, 01);
		LocalDate dateEnd = LocalDate.of(2026, 05, 01);

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDERS_CLIENT_URL + "/" + idClient + "/" + dateStart + "/" + dateEnd;

		try {
			ResponseEntity<List<Integer>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
					new ParameterizedTypeReference<List<Integer>>() {
					});

			assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

			List<Integer> ordersDetail = responseEntity.getBody();
			assertNull(ordersDetail);
		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
		}
	}

	@Test
	void getOrdersIdByIdArticleIsGood() {

		String idArticle = "A0001";

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDERS_ARTICLE_URL + "/" + idArticle;

		ResponseEntity<List<Integer>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Integer>>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<Integer> ordersId = responseEntity.getBody();
		assertNotNull(ordersId);
		assertFalse(ordersId.isEmpty());
	}

	@Test
	void getOrdersIdByIdArticleIsBad() {

		String idArticle = "NoArticle";

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDERS_ARTICLE_URL + "/" + idArticle;

		try {
			ResponseEntity<List<Integer>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
					new ParameterizedTypeReference<List<Integer>>() {
					});

			assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

			List<Integer> ordersDetail = responseEntity.getBody();
			assertNull(ordersDetail);
		} catch (HttpClientErrorException ex) {
			assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
		}

	}

	@Test
	void updateOrderStateIsGood() {
		Order updateOrder = new Order();
		updateOrder.setIdOrder(10);
		updateOrder.setIdClient("C0003");
		updateOrder.setAddress("new address here");
		updateOrder.setDtOrder(LocalDate.now());
		updateOrder.setState(StateOrder.REJECTED);
		updateOrder.setTotalOrderPrice(123.56);
		updateOrder.setTypePayment(TypePayment.PAY_TRANSFER);

		int idOrder = 10;

		HttpHeaders headers = createHeaders();

		HttpEntity<Order> requestEntity = new HttpEntity<>(updateOrder, headers);

		String url = ORDER_STATE_URL + "/" + idOrder;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.PUT, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	void updateOrderStateIsBadForUrlNotEquals() {
		Order updateOrder = new Order();
		updateOrder.setIdOrder(30);
		updateOrder.setIdClient("C0003");
		updateOrder.setAddress("new address here");
		updateOrder.setDtOrder(LocalDate.now());
		updateOrder.setState(StateOrder.REJECTED);
		updateOrder.setTotalOrderPrice(123.56);
		updateOrder.setTypePayment(TypePayment.PAY_TRANSFER);

		int idOrder = 9;

		HttpHeaders headers = createHeaders();

		HttpEntity<Order> requestEntity = new HttpEntity<>(updateOrder, headers);

		String url = ORDER_STATE_URL + "/" + idOrder;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.PUT, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

	}
	
	@Test
	void updateOrderStateIsBadForOrderNotFound() {
		Order updateOrder = new Order();
		updateOrder.setIdOrder(-1);
		updateOrder.setIdClient("C0003");
		updateOrder.setAddress("new address here");
		updateOrder.setDtOrder(LocalDate.now());
		updateOrder.setState(StateOrder.REJECTED);
		updateOrder.setTotalOrderPrice(123.56);
		updateOrder.setTypePayment(TypePayment.PAY_TRANSFER);

		int idOrder = -1;

		HttpHeaders headers = createHeaders();

		HttpEntity<Order> requestEntity = new HttpEntity<>(updateOrder, headers);

		String url = ORDER_STATE_URL + "/" + idOrder;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.PUT, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}


	@Test
	void closeOrderIsGood() {

		int idOrder = 14;

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_CLOSE_URL + "/" + idOrder;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.PUT, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});
		// CHANGE ORDER TO VERIFY AGAIN
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	void closeOrderIsBadForOrderNotFound() {
		int idOrder = -1;

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_CLOSE_URL + "/" + idOrder;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.PUT, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}

	@Test
	void closeOrderIsBadForConfirmedState() {
		int idOrder = 8;

		HttpHeaders headers = createHeaders();

		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		String url = ORDER_CLOSE_URL + "/" + idOrder;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.PUT, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());


	}

}
