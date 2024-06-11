package com.ec;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.web.client.RestTemplate;

import com.ec.model.Article;
import com.ec.model.Cart;

@SpringBootTest
public class CartControllerTest {
	/*
	private static final String BASE_URL = "http://localhost:8080";
	private static final String CART_URL = BASE_URL + "/cart";
	private static final String ARTICLES_URL = BASE_URL + "/articles";
	private static final String ONLY_ARTICLES_URL = "/articles";
	private static final String CART_TOTAL_URL = CART_URL + "/total";

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
	void getAllArticles() {

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<List<Article>> responseEntity = sendRequest(ARTICLES_URL, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Article>>() {
				});

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<Article> articles = responseEntity.getBody();
		assertNotNull(articles);
		assertFalse(articles.isEmpty());
	}

	@Test
	void addArticleToCartIsGood() {
		Article article = new Article();
		article.setIdArticle("A0001");
		article.setName("Laptop");
		article.setDescr("High-performance laptop");
		article.setPrice(1200.0);
		article.setQtaAvailable(50);

		String idClient = "C0001";
		int cartProgr = 1;
		int qtaOrdered = 2;

		HttpHeaders headers = createHeaders();

		HttpEntity<Article> requestEntity = new HttpEntity<>(article, headers);

		String url = CART_URL + "/" + idClient + "/" + cartProgr + "/" + qtaOrdered;

		ResponseEntity<Void> responseEntity = sendRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	@Test
	void addArticleToCartIsBad() {
		Article article = new Article();
		article.setIdArticle("A0021");
		article.setName("Test Article");
		article.setPrice(10.0);
		article.setQtaAvailable(100);

		String idClient = "C0010";
		int cartProgr = 1;
		int qtaOrdered = 2;

		HttpHeaders headers = createHeaders();

		HttpEntity<Article> requestEntity = new HttpEntity<>(article, headers);

		String url = CART_URL + "/" + idClient + "/" + cartProgr + "/" + qtaOrdered;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}

	@Test
	void createNewCartAndAddArticlesIsGood() {
		Article article = new Article();
		article.setIdArticle("A0002");
		article.setName("Smartphone");
		article.setDescr("Latest model smartphone");
		article.setPrice(800.00);
		article.setQtaAvailable(100);

		String idClient = "C0003";
		int cartProgr = 1;
		int qtaOrdered = 10;

		Cart newCart = new Cart();
		newCart.setIdClient(idClient);
		newCart.setCartProgr(cartProgr);
		newCart.setIdArticle(article.getIdArticle());
		newCart.setQtaOrdered(qtaOrdered);
		newCart.setUnitPrice(article.getPrice());
		newCart.setTotalPrice(article.getPrice() * qtaOrdered);

		HttpHeaders headers = createHeaders();

		HttpEntity<Cart> requestEntity = new HttpEntity<>(newCart, headers);

		String url = CART_URL + "/" + idClient + "/" + cartProgr + "/" + qtaOrdered;

		ResponseEntity<Void> responseEntity = sendRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
	}

	@Test
	void createNewCartAndAddArticlesIsBad() {
		Article article = new Article();
		article.setIdArticle("A002");
		article.setName("Smartphone");
		article.setDescr("Latest model smartphone");
		article.setPrice(800.00);
		article.setQtaAvailable(100);

		String idClient = "C0003";
		int cartProgr = 1;
		int qtaOrdered = 10;

		Cart newCart = new Cart();
		newCart.setIdClient(idClient);
		newCart.setCartProgr(cartProgr);
		newCart.setIdArticle(article.getIdArticle());
		newCart.setQtaOrdered(qtaOrdered);
		newCart.setUnitPrice(article.getPrice());
		newCart.setTotalPrice(article.getPrice() * qtaOrdered);

		HttpHeaders headers = createHeaders();

		HttpEntity<Cart> requestEntity = new HttpEntity<>(newCart, headers);

		String url = CART_URL + "/" + idClient + "/" + cartProgr + "/" + qtaOrdered;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.POST, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	void deleteArticleIsGood() {
		String idClient = "C0010";
		int cartProgr = 1;
		String idArticle = "A0001";

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = CART_URL + "/" + idClient + "/" + cartProgr + "/" + idArticle;

		ResponseEntity<Void> responseEntity = sendRequest(url, HttpMethod.DELETE, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void deleteArticleIsBad() {
		String idClient = "C0001";
		int cartProgr = 0;
		String idArticle = "A0001";

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = CART_URL + "/" + idClient + "/" + cartProgr + "/" + idArticle;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.DELETE, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}

	@Test
	void deleteArticlesIsGood() {

		String idClient = "C0002";
		int cartProgr = 2;

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = CART_URL + ONLY_ARTICLES_URL + "/" + idClient + "/" + cartProgr;

		ResponseEntity<Void> responseEntity = sendRequest(url, HttpMethod.DELETE, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void deleteArticlesIsBad() {

		String idClient = "C0002";
		int cartProgr = 3;

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = ARTICLES_URL + "/" + idClient + "/" + cartProgr;

		ResponseEntity<Void> responseEntity = sendTestRequest(url, HttpMethod.DELETE, requestEntity,
				new ParameterizedTypeReference<Void>() {
				});
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}

	@Test
	void getArticleIsGood() {
		Cart cartExpected = new Cart();
		cartExpected.setIdArticle("A0001");
		cartExpected.setCartProgr(1);
		cartExpected.setIdClient("C0010");
		cartExpected.setQtaOrdered(2);
		cartExpected.setTotalPrice(4.00);
		cartExpected.setUnitPrice(2.00);
		
		String idClient = "C0010";
		int cartProgr = 1;
		String idArticle = "A0001";

		HttpHeaders headers = createHeaders();

		HttpEntity<String> requestEntity = new HttpEntity<>(headers);

		String url = CART_URL + "/" + idClient + "/" + cartProgr + "/" + idArticle;

		ResponseEntity<Cart> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<Cart>() {
				});
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		Cart cartActual = responseEntity.getBody();
		assertNotNull(cartActual);

		assertEquals(cartExpected, cartActual);
	}

	@Test
	void getArticleIsBad() {
		String idClient = "C0099";
	    int cartProgr = 0;
	    String idArticle = "new";

	    HttpHeaders headers = createHeaders();

	    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

	    String url = CART_URL + "/" + idClient + "/" + cartProgr + "/" + idArticle;

	    ResponseEntity<Cart> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Cart>() {});
	    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

	    Cart cartActual = responseEntity.getBody();
	    assertNull(cartActual);
	}

	@Test
	void getAllArticlesForCartIsGood() {
	    String idClient = "C0002";
	    int cartProgr = 2;

	    HttpHeaders headers = createHeaders();

	    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

 	    String url = CART_URL + ONLY_ARTICLES_URL + "/" + idClient + "/" + cartProgr;

	    ResponseEntity<List<Cart>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Cart>>() {});
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	    List<Cart> articlesInCartActual = responseEntity.getBody();
	    assertFalse(articlesInCartActual.isEmpty());
	    assertNotNull(articlesInCartActual);
	}

	@Test
	void getAllArticlesForCartIsBad() {
	    String idClient = "C00011";
	    int cartProgr = 0;

	    HttpHeaders headers = createHeaders();

	    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

	    String url = CART_URL + ONLY_ARTICLES_URL + "/" + idClient + "/" + cartProgr;

	    ResponseEntity<List<Cart>> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Cart>>() {});
	    assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

	    List<Cart> articlesInCartActual = responseEntity.getBody();
	    assertNull(articlesInCartActual);
	}


	@Test
	void getComputeTotalPriceIsGood() {
	    String idClient = "C0002";
	    int cartProgr = 2;

	    HttpHeaders headers = createHeaders();

	    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

	    String url = CART_TOTAL_URL + "/" + idClient + "/" + cartProgr;

	    ResponseEntity<Double> responseEntity = sendRequest(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Double>() {});
	    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	    Double actualTotalPrice = responseEntity.getBody();
	    Double expectedTotalPrice = 14000.00;
	    assertEquals(expectedTotalPrice, actualTotalPrice);
	}
*/
}
