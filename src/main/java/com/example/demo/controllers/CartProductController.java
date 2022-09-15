package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.CartProduct;
import com.example.demo.domain.ItemDto;
import com.example.demo.domain.Product;
import com.example.demo.Service.CartProductService;

@RestController
@RequestMapping("/cartproduct")
@CrossOrigin(origins = "*")
public class CartProductController {
	@Autowired
	CartProductService cartProductService;

	/*
	@GetMapping(value = { "", "/" })
	public List<CartProduct> getAllProducts() {
		return cartProductService.getAllProducts();
	}
	*/

	@GetMapping(value = "/{userId}")
	public List<CartProduct> getCart(@PathVariable long userId) {
		return cartProductService.getCart(userId);
	}

	@GetMapping(value = "/cardtotal/{cardId}")
	public int getCardTotal(@PathVariable int cardId) {
		return cartProductService.getCardTotal(cardId);
	}

	@PostMapping(value = {"", "/addProductToCart"})
	public void addItemToCart(@RequestBody ItemDto item) {
		cartProductService.addItemToCart(item);
	}
	
	@PostMapping(value = {"/addAllProducts"})
	public void addAllItemToCart(@RequestBody List<ItemDto> items) {
		cartProductService.addAllItemToCart(items);
	}
	
	@DeleteMapping(value = {"/deleteproduct/{cartProductId}", "/buyproduct/{cartProductId}"})
	public void deleteProductFromCart(@PathVariable int cartProductId) {
		cartProductService.deleteProductFromCart(cartProductId);
	}
	
	@DeleteMapping(value = {"/deleteCart/{userId}", "/buy/{userId}"})
	public void deleteCart(@PathVariable Long userId) {
		cartProductService.deleteCart(userId);
	}
	

}
