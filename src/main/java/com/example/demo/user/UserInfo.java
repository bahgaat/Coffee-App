package com.example.demo.user;

public class UserInfo {
	
	String username;
	String Password;
	
	UserInfo(){}
	
	
	public UserInfo(String username, String password) {
		this.username = username;
		Password = password;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
	
}
