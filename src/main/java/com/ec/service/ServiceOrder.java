package com.ec.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ec.exception.ArticleNotFoundException;
import com.ec.exception.ClientNotFoundException;
import com.ec.exception.NoContentException;
import com.ec.exception.OrderAlreadyExistsException;
import com.ec.exception.OrderNotConfirmedException;
import com.ec.exception.OrderNotFoundException;
import com.ec.model.Article;
import com.ec.model.Cart;
import com.ec.model.Client;
import com.ec.model.Order;
import com.ec.model.OrderDetail;
import com.ec.model.doubleKey.OrderDetailID;
import com.ec.model.enums.StateOrder;
import com.ec.repository.ArticleRepository;
import com.ec.repository.CartRepository;
import com.ec.repository.ClientRepository;
import com.ec.repository.OrderDetailRepository;
import com.ec.repository.OrderRepository;
import com.ec.service.interfaces.OrderFunctions;

import jakarta.transaction.Transactional;


@Service
public class ServiceOrder implements OrderFunctions {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ClientRepository clientRepository;

	
	@Override
	public Optional<Order> addOrder(Order order, String idClient) throws ClientNotFoundException, OrderAlreadyExistsException {
		Optional<Client> client = clientRepository.findById(idClient);
		Optional<Order> orderAlreadyExist = orderRepository.findByIdOrder(order.getIdOrder());
		
		if (orderAlreadyExist.isPresent()) {
			throw new OrderAlreadyExistsException(order.getIdOrder());
		}
		
		if (client.isEmpty()) {
			throw new ClientNotFoundException(idClient);
		}

		order.setIdClient(idClient);
		order.setAddress(client.get().getAddress());
		order.setDtOrder(LocalDate.now());
		order.setState(StateOrder.PROGRESS);
		order.setTotalOrderPrice(0);
		order.setTypePayment(client.get().getTypePayment());
		Order savedOrder = orderRepository.save(order);
		return Optional.ofNullable(savedOrder);

	}

	@Override
	public Optional<Order> getOrder(int idOrder) {
		return orderRepository.findByIdOrder(idOrder);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public boolean updateOrder(Order order) throws OrderNotFoundException {
		if (!orderRepository.existsById(order.getIdOrder())) {
			throw new OrderNotFoundException(order.getIdOrder());
		}
		return orderRepository.save(order) != null;
	}
	
	@Override
	public boolean addArticle(int idOrder, String idArticle, int qtaOrdered) throws ArticleNotFoundException, NoContentException {
		Optional<Article> optionalArticle = articleRepository.findById(idArticle);
		if (!optionalArticle.isPresent()) {
			throw new ArticleNotFoundException(idArticle);
		}

		Article articleExist = optionalArticle.get();
		if (articleExist.getQtaAvailable() < qtaOrdered) {
			throw new NoContentException("Quantity avaiable is less then " + qtaOrdered);
		}

		Optional<Order> optOrder = orderRepository.findById(idOrder);
		Order order = optOrder.get();

		orderRepository.save(order);

		Optional<OrderDetail> optOrderDetail = orderDetailRepository.findById(new OrderDetailID(order.getIdOrder(), idArticle));
		OrderDetail orderDetail;

		if (optOrderDetail.isPresent()) {
			orderDetail = optOrderDetail.get();
			orderDetail.setQtaOrdered(orderDetail.getQtaOrdered() + qtaOrdered);
			orderDetail.setTotalPrice(orderDetail.getTotalPrice() + (qtaOrdered * articleExist.getPrice()));
		} else {
			orderDetail = new OrderDetail();
			orderDetail.setIdArticle(idArticle);
			orderDetail.setIdOrder(idOrder);
			orderDetail.setQtaOrdered(qtaOrdered);
			orderDetail.setUnitPrice(articleExist.getPrice());
			orderDetail.setTotalPrice(qtaOrdered * articleExist.getPrice());
		}

		orderDetailRepository.save(orderDetail);
		return true;
	}

	@Override
	public boolean existsArticle(int idOrder, String idArticle) {
		return orderDetailRepository.existsById(new OrderDetailID(idOrder, idArticle));
	}

	@Override
	public boolean deleteArticle(int idOrder, String idArticle) {
		OrderDetailID odID = new OrderDetailID(idOrder, idArticle);
		return orderDetailRepository.findById(odID)
				.map(orderDetail -> { orderDetailRepository.delete(orderDetail); return true; })
				.orElse(false);
	}
	@Transactional
	@Override
	public boolean deleteAllArticles(int idOrder) {
		return orderDetailRepository.deleteByIdOrder(idOrder) == 1 ? true : false;

	}
	@Transactional
	@Override
	public synchronized boolean confirmOrder(int idOrder, String idClient, int cartProgr) throws OrderNotFoundException, ClientNotFoundException {
		Optional<Order> optionalOrder = orderRepository.findByIdOrder(idOrder);
		Optional<Client> optionalClient = clientRepository.findById(idClient);
		if (optionalOrder.isEmpty()) {
			throw new OrderNotFoundException(idOrder);
		}
		
		if (!optionalClient.isPresent()) {
			throw new ClientNotFoundException(idClient);
		}
		
		Order order = optionalOrder.get();
		order.setState(StateOrder.CONFIRMED);
		orderRepository.save(order);

		List<Cart> cartItems = cartRepository.findByIdClientAndCartProgr(idClient, cartProgr);

		for (Cart cartItem : cartItems) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setIdOrder(order.getIdOrder());
			orderDetail.setIdArticle(cartItem.getIdArticle());
			orderDetail.setQtaOrdered(cartItem.getQtaOrdered());
			orderDetail.setUnitPrice(cartItem.getUnitPrice());
			orderDetail.setTotalPrice(cartItem.getTotalPrice());

			orderDetailRepository.save(orderDetail);
			cartRepository.delete(cartItem);
			
			Article article = articleRepository.findById(cartItem.getIdArticle()).get();
	        article.setQtaAvailable(article.getQtaAvailable() - cartItem.getQtaOrdered());
	        articleRepository.save(article);
		}

		return true;
	}

	@Override
	public Optional<OrderDetail> getArticle(int idOrder, String idArticle) {
		OrderDetailID odID = new OrderDetailID(idOrder, idArticle);
		return orderDetailRepository.findById(odID);
	}

	@Override
	public List<OrderDetail> getAllArticles(int idOrder) {
		return orderDetailRepository.findByIdOrder(idOrder);
	}

	@Override
	public List<Integer> getOrdersId(String idClient) {
		return orderRepository
				.findByIdClient(idClient)
				.stream()
				.map(Order::getIdOrder)
				.collect(Collectors.toList());		
	}

	@Override
	public List<Integer> getOrdersIdByDates(String idClient, LocalDate dateStart, LocalDate dateEnd) {
	  return orderRepository
			.findByIdClientAndDtOrderBetween(idClient, dateStart, dateEnd)
		    .stream()
		    .map(Order::getIdOrder)
		    .collect(Collectors.toList());
	}


	@Override
	public List<Integer> getOrdersIdByArticle(String idArticle) {
	    return orderDetailRepository
	            .findByIdArticle(idArticle)
	            .stream()
	            .map(OrderDetail::getIdOrder)
	            .collect(Collectors.toList());
	}


	@Override
	public boolean updateOrderState(int idOrder, StateOrder state) throws OrderNotFoundException {
	    Optional<Order> orderOpt = orderRepository.findByIdOrder(idOrder);
	    if (orderOpt.isEmpty()) {
	        throw new OrderNotFoundException(idOrder);
	    }
	    Order order = orderOpt.get();
	    order.setState(state);
	    orderRepository.save(order); 
	    return true;
	}
	
	@Transactional
	@Override
	public boolean closeOrder(int idOrder) throws OrderNotConfirmedException, OrderNotFoundException {
	    Optional<Order> orderOpt = orderRepository.findByIdOrder(idOrder);
	    if (orderOpt.isEmpty()) {
	    	throw new OrderNotFoundException(idOrder); 
	    }
	    Order order = orderOpt.get();
	    if (order.getState() != StateOrder.CONFIRMED) {
	    	throw new OrderNotConfirmedException(idOrder); 	    }
	    order.setState(StateOrder.CLOSED);
	    orderRepository.save(order);
	    return true;
	}

}
