package com.ec.service.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ec.exception.ArticleNotFoundException;
import com.ec.exception.ClientNotFoundException;
import com.ec.exception.NoContentException;
import com.ec.exception.OrderAlreadyExistsException;
import com.ec.exception.OrderNotConfirmedException;
import com.ec.exception.OrderNotFoundException;
import com.ec.model.Order;
import com.ec.model.OrderDetail;
import com.ec.model.enums.StateOrder;

public interface OrderFunctions {
	Optional<Order> addOrder(Order order, String idClient) throws ClientNotFoundException, OrderAlreadyExistsException;
	Optional<Order> getOrder(int idOrder);
	List<Order> getAllOrders();
	boolean updateOrder(Order order) throws OrderNotFoundException;
	boolean addArticle(int idOrder, String idArticle, int qtaOrdered) throws ArticleNotFoundException, NoContentException;
	boolean existsArticle(int idOrder, String idArticle);
	boolean deleteArticle(int idOrder, String idArticle);
	boolean deleteAllArticles(int idOrder);
	boolean confirmOrder(int idOrder, String idClient, int cartProgr) throws OrderNotFoundException, ClientNotFoundException;
	Optional<OrderDetail> getArticle(int idOrder, String idArticle);
	List<OrderDetail> getAllArticles(int idOrder);
	List<Integer> getOrdersId(String idClient);
	List<Integer> getOrdersIdByDates(String idClient, LocalDate dateStart, LocalDate dateEnd);
	List<Integer> getOrdersIdByArticle(String idArticle);
	boolean updateOrderState(int idOrder, StateOrder state) throws OrderNotFoundException;
	boolean closeOrder(int idOrder) throws OrderNotConfirmedException, OrderNotFoundException;
	
	
	
	
	
	
	
	
	
}

