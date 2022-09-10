package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{

}
