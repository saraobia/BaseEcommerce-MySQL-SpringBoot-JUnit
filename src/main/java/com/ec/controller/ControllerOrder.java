package com.ec.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ec.exception.ArticleNotFoundException;
import com.ec.exception.ClientNotFoundException;
import com.ec.exception.NoContentException;
import com.ec.exception.OrderAlreadyExistsException;
import com.ec.exception.OrderNotConfirmedException;
import com.ec.exception.OrderNotFoundException;
import com.ec.model.Article;
import com.ec.model.Order;
import com.ec.model.OrderDetail;
import com.ec.service.interfaces.OrderFunctions;

@RestController
@RequestMapping("/")
public class ControllerOrder {

	@Autowired
	private OrderFunctions orderFunctions;

	@PostMapping("order/{idClient}")
	public ResponseEntity<Order> addOrder(@RequestBody Order order, 
										  @PathVariable("idClient") String idClient) throws ClientNotFoundException, OrderAlreadyExistsException {
		
		Optional<Order> savedOrder = orderFunctions.addOrder(order, idClient);
		return new ResponseEntity<>(savedOrder.get(), HttpStatus.CREATED);
	}

	@GetMapping("order/{idOrder}")
	public ResponseEntity<Optional<Order>> getOrder(@PathVariable("idOrder") Integer idOrder) {
		
		Optional<Order> order = orderFunctions.getOrder(idOrder);
		return order.isEmpty() 
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(order, HttpStatus.OK);
	}

	@GetMapping("orders")
	public ResponseEntity<List<Order>> getAllOrders() {
		
		List<Order> orders = orderFunctions.getAllOrders();
		return orders.isEmpty() 
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(orders, HttpStatus.OK);
	}

	
	@PutMapping("order/{idOrder}")
	public ResponseEntity<Void> updateOrder(@PathVariable("idOrder") Integer idOrder, 
											@RequestBody Order order) throws OrderNotFoundException {
	   
		boolean updateSuccesfully= orderFunctions.updateOrder(order);
	    return updateSuccesfully 
	    		? new ResponseEntity<>(HttpStatus.OK)
	    		:  new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	
	@PostMapping("order/{idOrder}/{qtaOrdered}")
	public ResponseEntity<Void> addArticleToOrder(@RequestBody Article article, 
												  @PathVariable("idOrder") Integer idOrder,
												  @PathVariable("qtaOrdered") int qtaOrdered) throws ArticleNotFoundException, NoContentException {
		
		boolean addArticleSuccessful = orderFunctions.addArticle(idOrder, article.getIdArticle(), qtaOrdered);
		return addArticleSuccessful 
				? new ResponseEntity<>(HttpStatus.OK) 
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	

	@DeleteMapping("order/{idOrder}/{idArticle}")
	public ResponseEntity<Void> deleteArticleToOrder(@PathVariable("idOrder") Integer idOrder,
													 @PathVariable("idArticle") String idArticle) {
		
		boolean deleteArticleSuccesfull = orderFunctions.deleteArticle(idOrder, idArticle);
		return deleteArticleSuccesfull
				? new ResponseEntity<>(HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("order/articles/{idOrder}")
	public ResponseEntity<Void> deleteAllArticleToOrder(@PathVariable("idOrder") Integer idOrder) {
		
		boolean deleteSuccessfully = orderFunctions.deleteAllArticles(idOrder);
		return deleteSuccessfully
				? new ResponseEntity<>(HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("order/state/{idOrder}/{idClient}/{cartProgr}")
	public ResponseEntity<Void> confirmOrder(@PathVariable("idOrder") Integer idOrder, 
											   @PathVariable("idClient") String idClient, 
											   @PathVariable("cartProgr") int cartProgr) throws OrderNotFoundException, ClientNotFoundException {
		
		boolean confirmSuccessfully = orderFunctions.confirmOrder(idOrder, idClient, cartProgr);
		return confirmSuccessfully
				? new ResponseEntity<>(HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("order/article/{idOrder}/{idArticle}")
	public ResponseEntity<Optional<OrderDetail>> getArticle(@PathVariable("idOrder") Integer idOrder,
															@PathVariable("idArticle") String idArticle) {
		
		Optional<OrderDetail> orderDetail = orderFunctions.getArticle(idOrder, idArticle);
		return orderDetail.isPresent() 
				? new ResponseEntity<>(orderDetail, HttpStatus.OK) 
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("order/articles/{idOrder}")
	public ResponseEntity<List<OrderDetail>> getAllArticle(@PathVariable("idOrder") Integer idOrder) {
		
		List<OrderDetail> articlesInOrder = orderFunctions.getAllArticles(idOrder);
		return !articlesInOrder.isEmpty() 
				? new ResponseEntity<>(articlesInOrder, HttpStatus.OK) 
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping("orders/client/{idClient}")
	public ResponseEntity<List<Integer>> getOrdersId(@PathVariable("idClient") String idClient) {
		
		List<Integer> lsIdOrders = orderFunctions.getOrdersId(idClient);
		return !lsIdOrders.isEmpty() 
				? new ResponseEntity<>(lsIdOrders, HttpStatus.OK) 
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping("orders/client/{idClient}/{dateStart}/{dateEnd}")
	public ResponseEntity<List<Integer>> getOrdersIdByDates(@PathVariable("idClient") String idClient,
															@PathVariable("dateStart") LocalDate dateStart,
															@PathVariable("dateEnd") LocalDate dateEnd) {
		
		List<Integer> lsIdOrdersFromDates = orderFunctions.getOrdersIdByDates(idClient, dateStart, dateEnd);
		return !lsIdOrdersFromDates.isEmpty() 
				? new ResponseEntity<>(lsIdOrdersFromDates, HttpStatus.OK) 
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@GetMapping("orders/article/{idArticle}")
	public ResponseEntity<List<Integer>> getOrdersIdByIdArticle(@PathVariable("idArticle") String idArticle) {
	   
		List<Integer> lsIdOrdersFromArticle = orderFunctions.getOrdersIdByArticle(idArticle);
	    return !lsIdOrdersFromArticle.isEmpty()
	            ? new ResponseEntity<>(lsIdOrdersFromArticle, HttpStatus.OK)
	            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("order/state/{idOrder}")
	public ResponseEntity<Void> updateOrderState(@PathVariable("idOrder") Integer idOrder, 
	                                             @RequestBody Order order) throws OrderNotFoundException {
	  
		if (!idOrder.equals(order.getIdOrder())) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }

	    boolean successUpdatedState = orderFunctions.updateOrderState(idOrder, order.getState());
	    if (successUpdatedState) {
	        return new ResponseEntity<>(HttpStatus.OK);
	    }
	    return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("order/state/close/{idOrder}")
	public ResponseEntity<String> closeOrder(@PathVariable("idOrder") Integer idOrder) throws OrderNotConfirmedException, OrderNotFoundException {
	    boolean successCloseOrder = orderFunctions.closeOrder(idOrder);
	    return successCloseOrder
	    		? new ResponseEntity<>(HttpStatus.OK)
	    	    : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    		
	   
	}


}
