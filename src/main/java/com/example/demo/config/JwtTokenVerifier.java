package com.example.demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.demo.user.Role;
import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;



public class JwtTokenVerifier extends OncePerRequestFilter{

	@Autowired
	@Value("${jwt.secret}")
	String jwtSecret;	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authorizationHeader = request.getHeader("Authorization");
		Algorithm secretKey = Algorithm.HMAC256("secret".getBytes());
		
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
        	//String[] roles = decodeJwt.getClaim("roles").asArray(String.class);
        	/*
			 * Arrays.stream(roles).forEach(role -> { authorities.add(new
			 * SimpleGrantedAuthority(role)); });
			 */
        	
        	UsernamePasswordAuthenticationToken authToken = 
        			new UsernamePasswordAuthenticationToken(username, null, authorities);
        	
            
        	
        	SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
            

			/*
			 * List<Role> roles = new ArrayList<>(); roles.add(Role.Employer);
			 * roles.add(Role.Customer); Collection<SimpleGrantedAuthority> auths =
			 * (Collection<SimpleGrantedAuthority>) getAuthorities(roles);
			 * 
			 * Authentication authentication = new UsernamePasswordAuthenticationToken(
			 * username, null, auths );
			 */


        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }


        
		
	}
	
	/*
	 * public Collection<? extends GrantedAuthority> getAuthorities(
	 * Collection<Role> roles) { List<GrantedAuthority> authorities = new
	 * ArrayList<>(); for (Role role: roles) { authorities.add(new
	 * SimpleGrantedAuthority(role.getName()));
	 * 
	 * }
	 * 
	 * return authorities; }
	 */
	
}
