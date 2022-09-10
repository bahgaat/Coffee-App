package com.example.demo.config;

import java.io.IOException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.Service.UserService;
import com.example.demo.domain.UserDTO;
import com.example.demo.user.Role;
import com.example.demo.user.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;



public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	@Value("${jwt.secret}")
	String jwtSecret;
	
	

	public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.setFilterProcessesUrl("/login");
	}
	
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		//String username = request.getParameter("username");
		UserInfo authenticationRequest;
		try {
			authenticationRequest = new ObjectMapper()
			        .readValue(request.getInputStream(), UserInfo.class);
			
			String username = authenticationRequest.getUsername();
			//String password = passwordEncoder.encode(authenticationRequest.getPassword());
			String password = authenticationRequest.getPassword();
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    password);
			
			Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null; 
	}

	

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		Algorithm secretKey = Algorithm.HMAC256("secret".getBytes());
		
		
		String token = JWT.create()
					.withSubject(authResult.getName())
					.withClaim("authorities", authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
					.withIssuedAt(new java.util.Date())
					.withExpiresAt(java.sql.Date.valueOf(LocalDate.now().plusDays(4)))
					.sign(secretKey);
		
        response.addHeader("token", token);
        
        
		/*
		 * // response body response.setContentType("application/json"); UserDTO user =
		 * userService.getUserByEmail(authResult.getName()); String userToJsonStr =
		 * gson.toJson(user); response.getWriter().write(userToJsonStr);
		 */
	}
	
	
	/*
	 * private Collection<? extends GrantedAuthority> getAuthorities(
	 * Collection<Role> roles) { List<GrantedAuthority> authorities = new
	 * ArrayList<>(); for (Role role: roles) { authorities.add(new
	 * SimpleGrantedAuthority(role.getName()));
	 * 
	 * }
	 * 
	 * return authorities; }
	 */
	
	

}
