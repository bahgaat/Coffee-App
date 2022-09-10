package com.example.demo.Service;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Item;
import com.example.demo.repos.ItemRepo;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepo itemRepo;
	
	public void addItems(List<Item> items)
	{
		itemRepo.saveAll(items);
	}
	
	public List<Item> getAllItems()
	{
		return itemRepo.findAll();
	}
	
	public List<Item> getWithFilter(Long quantity, Long price)
	{
		return itemRepo.getAllGreaterOrEqualFilter(quantity, price);
	}

	public void addItems(Item item) {
		itemRepo.save(item);
	}
	
	
	public Item getItem(Long id) {
		return itemRepo.findById(id).get();
	}
	
}