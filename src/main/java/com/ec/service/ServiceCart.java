package com.ec.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ec.exception.NoContentException;
import com.ec.exception.NotFoundException;
import com.ec.model.Article;
import com.ec.model.Cart;
import com.ec.model.doubleKey.CartID;
import com.ec.repository.ArticleRepository;
import com.ec.repository.CartRepository;
import com.ec.service.interfaces.CartFunctions;

@Service
public class ServiceCart implements CartFunctions {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public boolean addArticle(Article article, String idClient, int cartProgr, int qtaOrdered) throws NotFoundException {
		Optional<Article> optionalArticle = articleRepository.findById(article.getIdArticle());
		if (!optionalArticle.isPresent()) {
			throw new NotFoundException("Article not found: " + article.getIdArticle());

		}

		Article articleExist = optionalArticle.get();
		if (articleExist.getQtaAvailable() < qtaOrdered) {
			throw new NotFoundException("Quantity available is less than ordered: " + qtaOrdered);
		}
		
		cartProgr = resolveCartProgr(idClient, cartProgr);
		Cart cart = updateOrCreateCart(idClient, cartProgr, articleExist, qtaOrdered, qtaOrdered);
		return cartRepository.save(cart) != null;

	}
	
	@Override
	public Optional<Cart> createNewCartWithArticle(String idClient, Article article, int qtaOrdered) throws NotFoundException {
	    Optional<Article> optionalArticle = articleRepository.findById(article.getIdArticle());
	    if (!optionalArticle.isPresent()) {
	        throw new NotFoundException("Article not found: " + article.getIdArticle());
	    }

	    Article articleExist = optionalArticle.get();
	    if (articleExist.getQtaAvailable() < qtaOrdered) {
	        throw new NotFoundException("Quantity available is less than ordered: " + qtaOrdered);
	    }

	    Optional<Integer> maxCartProgrOpt = cartRepository.findMaxCartProgrByIdClient(idClient);
	    int newCartProgr = maxCartProgrOpt.orElse(0) + 1;

	    Cart newCart = new Cart();
	    newCart.setIdClient(idClient);
	    newCart.setCartProgr(newCartProgr);
	    newCart.setIdArticle(article.getIdArticle());
	    newCart.setQtaOrdered(qtaOrdered);
	    newCart.setUnitPrice(article.getPrice());
	    newCart.setTotalPrice(article.getPrice() * qtaOrdered);
	    cartRepository.save(newCart);

	    return Optional.ofNullable(newCart);
	}


	@Override
	public boolean existsArticle(String idClient, int cartProgr, String idArticle) {
		return cartRepository.existsById(new CartID(idClient, cartProgr, idArticle));
	}

	@Override
	public boolean deleteArticle(String idClient, int cartProgr, String idArticle) throws NoContentException {
		CartID cID = new CartID(idClient, cartProgr, idArticle);
		if (!cartRepository.existsById(cID)) {
			throw new NoContentException("Article not found: " + idArticle);
		}
		cartRepository.delete(cartRepository.findById(cID).get());
		return true;
	}

	@Override
	public boolean deleteAllArticles(String idClient, int cartProgr) {
		return (cartRepository.deleteByIdClientAndCartProgr(idClient, cartProgr) == 1) ? true : false;
	}

	@Override
	public Optional<Cart> getArticle(String idClient, int cartProgr, String idArticle) {
		CartID cID = new CartID(idClient, cartProgr, idArticle);
		return cartRepository.findById(cID);
	}

	@Override
	public List<Cart> getAllArticles(String idClient, int cartProgr) {
		return cartRepository.findByIdClientAndCartProgr(idClient, cartProgr);
	}

	@Override
	public double computePriceTotal(String idClient, int cartProgr) throws NotFoundException {
	    List<Cart> cartItems = getAllArticles(idClient, cartProgr);
	    if (cartItems.isEmpty()) {
	        throw new NotFoundException("Cart not found for client: " + idClient + " or the number of cartPror is wrong: " + cartProgr);
	    }
	    return cartItems.stream()
                .mapToDouble(cart -> cart.getUnitPrice() * cart.getQtaOrdered())
                .sum();
	}


	@Override
	public List<Article> getListOfArticles() {
		List<Article> list = new ArrayList<>();
		articleRepository.findAll().forEach(list::add);
		return list;
	}
	

    private int resolveCartProgr(String idClient, int cartProgr) {
        if (cartProgr == 0) {
            return cartRepository.findMaxCartProgrByIdClient(idClient).orElse(0) + 1;
        }
        return cartProgr;
    }

    private Cart updateOrCreateCart(String idClient, int cartProgr, Article article, int qtaOrdered, double price) {
        Optional<Cart> optCart = cartRepository.findByIdClientAndIdArticle(idClient, article.getIdArticle());
        Cart cart;

        if (optCart.isPresent()) {
            cart = optCart.get();
            cart.setQtaOrdered(cart.getQtaOrdered() + qtaOrdered);
            cart.setTotalPrice(cart.getUnitPrice() * cart.getQtaOrdered());
        } else {
            cart = new Cart();
            cart.setIdClient(idClient);
            cart.setCartProgr(cartProgr);
            cart.setIdArticle(article.getIdArticle());
            cart.setQtaOrdered(qtaOrdered);
            cart.setUnitPrice(price);
            cart.setTotalPrice(price * qtaOrdered);
        }
        return cart;
    }

}
