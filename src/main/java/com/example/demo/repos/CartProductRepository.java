package com.example.demo.repos;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.CartProduct;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Integer>{
	@Query(value="select * from cart_product where cart_id=?1",nativeQuery = true)
	List<CartProduct> findAllByCartId(int cardId);

	/*
	 * @Query(value="select * from cart_product s where cart_id=?1 AND item_id=?2"
	 * ,nativeQuery = true) Optional<CartProduct> findByCartIdAndItemId(int card_id,
	 * int item_id);
	 */
	
	Optional<CartProduct> findByCart_idAndProduct_productId(int cartId, int productId);
	
	@Modifying
	@Transactional
	@Query(value="delete from cart_product c where c.cart_id=?1",nativeQuery = true)
	void deleteByCart_id(int cartId);
	
	void deleteByProduct_productId(int productId); 
}
