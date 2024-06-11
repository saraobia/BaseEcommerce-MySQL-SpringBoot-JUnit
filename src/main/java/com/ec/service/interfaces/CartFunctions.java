package com.ec.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.ec.exception.NoContentException;
import com.ec.exception.NotFoundException;
import com.ec.model.Article;
import com.ec.model.Cart;

public interface CartFunctions {
	boolean addArticle(Article article, String idClient, int cartProgr, int qtaOrdered) throws NotFoundException;
	boolean existsArticle(String idClient, int cartProgr, String idArticle);
	boolean deleteArticle(String idClient, int cartProgr, String idArticle) throws NoContentException;
	boolean deleteAllArticles(String idClient, int cartProgr);
	Optional<Cart> getArticle(String idClient, int cartProgr, String idArticle);
	List<Cart> getAllArticles(String idClient, int cartProgr);
	double computePriceTotal(String idClient, int cartProgr) throws NotFoundException;
	List<Article> getListOfArticles();
	Optional<Cart> createNewCartWithArticle(String idClient, Article article, int qtaOrdered) throws NotFoundException;
}
