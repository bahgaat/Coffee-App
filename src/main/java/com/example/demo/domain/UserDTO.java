package com.example.demo.domain;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {

	public Long id;
	public String username;
	public String email;
	
}
