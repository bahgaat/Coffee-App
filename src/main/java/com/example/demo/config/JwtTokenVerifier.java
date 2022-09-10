package com.example.demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.common.base.Strings;

import io.jsonwebtoken.JwtException;

@Component
public class JwtTokenVerifier extends OncePerRequestFilter{


	
	@Value("${jwt.secret}")
	private String jwtSecret;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authorizationHeader = request.getHeader("Authorization");
		Algorithm secretKey = Algorithm.HMAC256(jwtSecret.getBytes());
		
		if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
		
        String token = authorizationHeader.replace("Bearer ", "");
        
        try {
        	JWTVerifier verifier = JWT.require(secretKey).build();
        	DecodedJWT decodeJwt = verifier.verify(token);
        	String username = decodeJwt.getSubject();
        	
        	Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        	
        	UsernamePasswordAuthenticationToken authToken = 
        			new UsernamePasswordAuthenticationToken(username, null, authorities);
        	
        	
        	SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
            
        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }


        
		
	}
	
}
