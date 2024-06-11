package com.ec.repository;




import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ec.model.Order;


public interface OrderRepository extends JpaRepository<Order, Integer> {
	Optional<Order> findByIdOrder(Integer idOrder);
	List<Order> findByIdClient(String idClient);
	
	//@Query("SELECT o FROM Order o WHERE o.dtOrder BETWEEN :dateStart AND :dateEnd AND o.idClient = :idClient")
	//List<Order> findByDtOrderDateBetweenAndIdClient(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd, @Param("idClient") String idClient);
	
	List<Order> findByIdClientAndDtOrderBetween(@Param("idClient") String idClient, @Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);

}
