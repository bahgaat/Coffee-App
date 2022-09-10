package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.CategoryService;
import com.example.demo.domain.Category;
import com.example.demo.domain.Item;

@RestController
@RequestMapping("/category")
public class CategoryApi {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(method = RequestMethod.GET, path = "/getAll")
	public List<Category> getAllCat() {
		
		return categoryService.getAllCat();
		
	}

	@RequestMapping(method = RequestMethod.POST, path = "/addCategory")
	public void addCategory(@RequestBody List<Category> cats) {
		categoryService.addCats(cats);
		
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/deleteCategory/{id}")
	public void deleteCategory(@PathVariable int id) {
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/updateCategory/{id}")
	public void updateCategory(@PathVariable int id) {
		
	}

}
