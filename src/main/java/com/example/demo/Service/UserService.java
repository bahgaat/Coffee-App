package com.example.demo.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.domain.UserDTO;
import com.example.demo.repos.RoleRepo;
import com.example.demo.repos.UserRepo;

import ch.qos.logback.classic.Logger;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserService implements UserDetailsService{
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	/*
	 * Add validation to below methods
	 * */
	
	
	public User saveUser(User user)
	{
		user.roles.forEach(role -> {
			saveRole(role);
		});
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepo.save(user);
	}
	
	public Role saveRole(Role role)
	{
		return roleRepo.save(role);
	}
	
	public User setRoleToUser(String username, String roleName)
	{
		User user = userRepo.findByUsername(username);
		Role role = roleRepo.findByRoleName(roleName);
		
		user.roles.add(role);
		return user;
	}
	
	public UserDTO getUser(String username)
	{
		User user = userRepo.findByUsername(username);
		return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
	}
	
	public UserDTO getUser(Long id)
	{
		User user = userRepo.findById(id).orElse(null);
		return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
	}
	
	public UserDTO getUserByEmail(String email)
	{
		User user = userRepo.findByEmail(email);
		return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
	}
	
	public List<User> getAllUsers()
	{
		
		
		List<User> users = userRepo.findAll();
		return users;
	}
	
	

	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		
		User user = userRepo.findByEmail(email);
		
		if(user == null)
		{
			new UsernameNotFoundException("Email {} not found".formatted(email));			
		}
		
		Collection<SimpleGrantedAuthority> auths = new ArrayList<>();
		user.roles.forEach(role -> auths.add(new SimpleGrantedAuthority(role.roleName)));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), auths);
	}
	
	
}
