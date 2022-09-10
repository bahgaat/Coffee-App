package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.CategoryService;
import com.example.demo.Service.ItemService;
import com.example.demo.domain.Item;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/item")
public class ItemApi {

	@Autowired
	private ItemService itemService;
	
	@Cacheable(value = "itemsCache")
	@RequestMapping(method = RequestMethod.GET, path = "/items")
	public List<Item> getAllItems() {
		System.out.println("----------------TEST1----------------");
		return itemService.getAllItems();
	}
	
	
	@Cacheable(value = "itemsCache", key = "#id")
	@RequestMapping(method = RequestMethod.GET, path = "/item/{id}")
	public Item getItem(@PathVariable Long id) {
		System.out.println("----------------TEST2----------------");
		return itemService.getItem(id);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, path = "/itemsFilter")
	public List<Item> getItemsWithFilter( @RequestParam Long quantity, @RequestParam Long price) {
		
		
		
		return itemService.getWithFilter(quantity, price);
	}

	
	
	@CacheEvict(value= "itemsCache", allEntries = true)
	@RequestMapping(method = RequestMethod.POST, path = "/addItems")
	public void addItem(@RequestBody List<Item> items) {
		items.forEach(x -> {
			System.out.println(x);
		});
		itemService.addItems(items);
	}
	
	
	@CacheEvict(value= "itemsCache", allEntries = true)
	@RequestMapping(method = RequestMethod.POST, path = "/addItem")
	public void addItem(@RequestBody Item item) {
		itemService.addItems(item);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/deleteItem/{id}")
	public void deleteItem(@PathVariable int id) {
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/updateItem/{id}")
	public void updateItem(@PathVariable int id) {
		
	}
}
