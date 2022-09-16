package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer>{

}
