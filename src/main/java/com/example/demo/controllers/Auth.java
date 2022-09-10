package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.Service.UserService;
import com.example.demo.config.JwtUtil;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRequest;
import com.example.demo.domain.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.HttpHeaders;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class Auth {
	
	@Autowired
	UserService userService;
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@PostMapping("/register")
	@Operation(
		responses= {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class))),
					@ApiResponse(responseCode = "409", content = @Content(examples = @ExampleObject(name="example", value="java.lang.Exception: User Already Exists")))}
	)
	public ResponseEntity<Object> addUser(@RequestBody User user)
	{
		try {
			return ResponseEntity.created(null).body(userService.saveUser(user));		
			
		} catch (Exception e) {
			return new ResponseEntity<Object>(
			          e.toString(), new HttpHeaders(), HttpStatus.CONFLICT);
			
		}
		
	}
	
	@PostMapping("/login")
	@Operation(
			responses= {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponse.class))),
					@ApiResponse(responseCode = "400", content = @Content(examples = @ExampleObject(name="example", value="java.lang.Exception: Wrong User Info")))
			}
		)
	public ResponseEntity<Object> userLogin(@RequestBody UserRequest user)
	{
				
		try {
			User mainUser = userService.getUserByEmail(user.email);
			String token = jwtUtil.generateToken(mainUser, user.getPassword());
			
			UserResponse userRes = new UserResponse(mainUser.id, mainUser.username, mainUser.email, token);
			
			return ResponseEntity.ok().body(userRes);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.toString());
			
		}
		
	}
	
	
}
