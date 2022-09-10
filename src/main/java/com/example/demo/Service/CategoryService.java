package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Category;
import com.example.demo.domain.Item;
import com.example.demo.repos.CategoryRepo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	public void addCats(List<Category> cats)
	{
		categoryRepo.saveAll(cats);
	}
	
	public List<Category> getAllCat()
	{
		return categoryRepo.findAll();
	}
	

	
}
