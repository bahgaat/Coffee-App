package com.example.demo.config;


import java.time.LocalDate;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.domain.User;

@Component
public class JwtUtil{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	@Value("${jwt.secret}")
	String jwtSecret;
	

	
	public String generateToken(User user, String password) throws Exception
	{
		if(user == null)
		{
			throw new Exception("Wrong User Info");
		}
		
		String token = "";
		try {
			
			authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(), password)
                    );
			Algorithm secretKey = Algorithm.HMAC256(jwtSecret.getBytes());
			
			
			token = JWT.create()
					.withSubject(user.getUsername())
					.withClaim("authorities", user.getRoles().stream().map(x -> new String(x.getRoleName())).collect(Collectors.toList()))
					.withIssuedAt(new java.util.Date())
					.withExpiresAt(java.sql.Date.valueOf(LocalDate.now().plusDays(4)))
					.sign(secretKey);
			
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		return token;
	}
	
	

}
