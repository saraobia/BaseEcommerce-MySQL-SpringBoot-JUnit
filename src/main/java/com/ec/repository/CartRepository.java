package com.ec.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ec.model.Cart;
import com.ec.model.doubleKey.CartID;

import jakarta.transaction.Transactional;

public interface CartRepository extends JpaRepository<Cart, CartID> {
	
	@Transactional
	long deleteByIdClientAndCartProgr(String idClient, int cartProgr);
	List<Cart> findByIdClient(String idClient);
	List<Cart> findByIdClientAndCartProgr(String idClient, int cartProgr);
	Optional<Cart> findByIdClientAndIdArticle(String idClient, String idArticle);
	
	@Query("SELECT MAX(c.cartProgr) FROM Cart c WHERE c.idClient = :idClient" ) //nativeQuery = true;
	Optional<Integer> findMaxCartProgrByIdClient(@Param("idClient") String idClient);

	

}
