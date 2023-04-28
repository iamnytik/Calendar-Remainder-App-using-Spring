package com.example.model;

public class User{
	String username; // key
	String password;
	String phoneNumber;
	String email;
	
	public User() {}
	
	public User(String username, String password, String phoneNumber, String email) {
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	
}
