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

import com.ec.service.interfaces.CartFunctions;
import com.ec.exception.NoContentException;
import com.ec.exception.NotFoundException;
import com.ec.model.Article;
import com.ec.model.Cart;


@RestController
@RequestMapping("/")
public class ControllerCart {
	@Autowired
	private CartFunctions cartFunctions;
	
	@GetMapping("articles")
	public ResponseEntity<List<Article>> viewAllArticlesInTable(){
		List<Article> articles = cartFunctions.getListOfArticles();
		
		return !articles.isEmpty() 
				? new ResponseEntity<>(articles, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@PostMapping("cart/{idClient}/{cartProgr}/{qtaOrdered}")
	public ResponseEntity<Void> addArticleToCart(@RequestBody Article article, 
												 @PathVariable("idClient") String idClient, 
												 @PathVariable("cartProgr") int cartProgr, 
												 @PathVariable("qtaOrdered") int qtaOrdered ) throws NotFoundException {
	
		boolean addArticleSuccessfully = cartFunctions.addArticle(article, idClient, cartProgr, qtaOrdered);
		
		return addArticleSuccessfully 
				? new ResponseEntity<>(HttpStatus.CREATED) 
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	
	
	@PostMapping("cart/new/{idClient}/{qtaOrdered}")
	public ResponseEntity<Optional<Cart>> createNewCartAndAddArticle(@RequestBody Article article, 
																	@PathVariable("idClient") String idClient,  
																	@PathVariable("qtaOrdered") int qtaOrdered) throws NotFoundException {
	    Optional<Cart> newCart = cartFunctions.createNewCartWithArticle(idClient, article, qtaOrdered);
	    return new ResponseEntity<>(newCart, HttpStatus.CREATED);
	}

	
	@DeleteMapping("cart/{idClient}/{cartProgr}/{idArticle}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("idClient") String idClient, 
											  @PathVariable("cartProgr") int cartProgr, 
											  @PathVariable("idArticle") String idArticle) throws NoContentException {
		
		boolean deleteArticleSuccesfully = cartFunctions.deleteArticle(idClient, cartProgr, idArticle);
		return deleteArticleSuccesfully 
				? new ResponseEntity<>(HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@DeleteMapping("cart/articles/{idClient}/{cartProgr}")
	public ResponseEntity<Void> deleteAllArticles(@PathVariable("idClient") String idClient, 
												  @PathVariable("cartProgr") int cartProgr) {
		
		boolean deleteAllArticleSuccesfully = cartFunctions.deleteAllArticles(idClient, cartProgr);
		return deleteAllArticleSuccesfully 
				?  new ResponseEntity<>(HttpStatus.OK)
				:  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	

	@GetMapping("cart/{idClient}/{cartProgr}/{idArticle}")
	public ResponseEntity<Cart> getArticle(@PathVariable("idClient") String idClient, 
										   @PathVariable("cartProgr") int cartProgr, 
										   @PathVariable("idArticle") String idArticle) {
	 
		Optional<Cart> cartOptional = cartFunctions.getArticle(idClient, cartProgr, idArticle);	    
		return cartOptional.isPresent()
			? new ResponseEntity<>(cartOptional.get(), HttpStatus.OK)
			: new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}
	
	
	@GetMapping("cart/articles/{idClient}/{cartProgr}")
    public ResponseEntity<List<Cart>> getAllArticles(@PathVariable("idClient") String idClient, 
    												 @PathVariable("cartProgr") int cartProgr) {
		
        List<Cart> articlesInCart = cartFunctions.getAllArticles(idClient, cartProgr);
        return !articlesInCart.isEmpty()
        	? new ResponseEntity<>(articlesInCart, HttpStatus.OK)
        	: new ResponseEntity<>(HttpStatus.NO_CONTENT);
       
    }

	
	@GetMapping("cart/total/{idClient}/{cartProgr}")
	public ResponseEntity<Double> getComputeTotalPrice(@PathVariable("idClient") String idClient, 
													@PathVariable("cartProgr") int cartProgr) throws NotFoundException {
		
		double totalPrice = cartFunctions.computePriceTotal(idClient, cartProgr);
		return new ResponseEntity<>(totalPrice, HttpStatus.OK);
	}
	
}
