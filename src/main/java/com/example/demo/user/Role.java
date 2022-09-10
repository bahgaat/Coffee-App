package com.example.demo.user;

public enum Role {
	Employer("Employer"),
	Customer("Customer");
	
	String lable;

	private Role(String lable) {
		this.lable = lable;
	}
	
	public String getName()
	{
		return lable;
	}
}
