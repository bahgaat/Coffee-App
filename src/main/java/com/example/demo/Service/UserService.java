package com.example.demo.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	public User saveUser(User user) throws Exception
	{
		
		User searchUser = userRepo.findByEmail(user.email);
		if (searchUser != null) {
			throw new Exception("User already exists");
		}
		 
		
		
		try {
			
			if(user.roles != null)
			{
				user.roles.forEach(role -> {
					saveRole(role);
				});			
			}
			else
			{
				user.roles = new ArrayList<>();
				user.roles.add(new Role());
				user.roles.get(0).roleName = "customer";
				saveRole(user.roles.get(0));
			}
			
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
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
	
	
	public UserDTO getUserByEmailDTO(String email)
	{
		User user = userRepo.findByEmail(email);
		return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
	}
	
	public User getUserByEmail(String email)
	{
		User user = userRepo.findByEmail(email);
		return user;
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
