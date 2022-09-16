package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Cart;
import com.example.demo.domain.CartProduct;
import com.example.demo.domain.ItemDto;
import com.example.demo.domain.Product;
import com.example.demo.repos.CartProductRepository;
import com.example.demo.repos.ProductRepository;
import com.example.demo.repos.UserRepo;

@Service
public class CartProductService {
	@Autowired
    CartProductRepository cartProductRepository;
	@Autowired
	UserRepo userRepo;
	@Autowired
	ProductRepository productRepository;

	/*
    public List<CartProduct> getAllProducts() {
    return cartProductRepository.findAll();
    }

    public List<CartProduct> getCart(int cardId) {
	   return cartProductRepository.findAllByCartId(cardId);
    }

	 */


    public List<CartProduct> getCart(long userId) {
		int cartId = userRepo.findById(userId).get().getCart().getId();
		return cartProductRepository.findAllByCartId(cartId);
	}
    public int getCardTotal(int cardId) {
	    int t=0;
	    List<CartProduct> products=cartProductRepository.findAllByCartId(cardId);
	    for(CartProduct c: products) {
		    t+=c.quantity*c.product.productPrice;
	    }
	    return t;
    }

	public void addItemToCart(ItemDto item) {
		Cart cart = userRepo.findById(item.getUser_id()).get().getCart();
		
		int cartId = cart.getId();
		int itemId = item.getItem_id();
		int newQuantity = item.getQuantity();
		
		Product product = productRepository.findByproductId(itemId);
		CartProduct cartProduct = cartProductRepository.findByCart_idAndProduct_productId(cartId, itemId).orElse(null);
		
		if (cartProduct == null) {
			cartProduct = new CartProduct(newQuantity, cart, product);
			cartProductRepository.save(cartProduct);
		} else {
			int oldQuantity = cartProduct.getQuantity();
			cartProduct.setQuantity(oldQuantity + newQuantity);
		}
	}
	
	public void addAllItemToCart(List<ItemDto> items) {
		for(ItemDto item : items)
		{
			Cart cart = userRepo.findById(item.getUser_id()).get().getCart();
			
			int cartId = cart.getId();
			int itemId = item.getItem_id();
			int newQuantity = item.getQuantity();
			
			Product product = productRepository.findByproductId(itemId);
			CartProduct cartProduct = cartProductRepository.findByCart_idAndProduct_productId(cartId, itemId).orElse(null);
			
			if (cartProduct == null) {
				cartProduct = new CartProduct(newQuantity, cart, product);
				cartProductRepository.save(cartProduct);
			} else {
				int oldQuantity = cartProduct.getQuantity();
				cartProduct.setQuantity(oldQuantity + newQuantity);
			}
			
		}
	}
	
	public void deleteProductFromCart(int cartProductId) {
		cartProductRepository.deleteById(cartProductId);
	}
	
	public void deleteCart(Long userId) {
		int cartId = userRepo.findById(userId).get().getCart().getId();
		cartProductRepository.deleteByCart_id(cartId);
		/*
		 * List<CartProduct> cartList = cartProductRepository.findAllByCartId(cartId);
		 * for(CartProduct cProduct: cartList) { cartList.remove(cProduct); }
		 */
		
	}
	

}
