package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long>{

	@Query(value = "SELECT i From Item i WHERE i.price >= :price and i.quantity >= :quantity")
	List<Item> getAllGreaterOrEqualFilter(@Param("quantity") Long quantity, @Param("price") Long price);
}
