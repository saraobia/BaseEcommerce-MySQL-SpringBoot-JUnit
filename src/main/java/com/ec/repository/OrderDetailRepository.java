package com.ec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ec.model.OrderDetail;
import com.ec.model.doubleKey.OrderDetailID;

import jakarta.transaction.Transactional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailID> {
	@Transactional
	long deleteByIdOrder(int idOrder);
	
	List<OrderDetail> findByIdOrder(int idOrder);
	List<OrderDetail> findByIdArticle(String idArticle);
	
}
