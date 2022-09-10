package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserService;
import com.example.demo.domain.User;
import com.example.demo.domain.UserDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/login")
@Slf4j
public class Auth {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public User addUser(@RequestBody User user)
	{
		return userService.saveUser(user);
	}
	
	@PostMapping("")
	public ResponseEntity<?> userLogin(@RequestBody UserDTO user)
	{
		System.out.println("---------------------------In Custom Auth class---------------------------");
		log.info("---------------------------In Custom Auth class---------------------------");
		return ResponseEntity.ok().body(userService.getUserByEmail(user.username));
	}
	
	
}
