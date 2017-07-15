package com.ryg.expandable;

public class User {
	private String username;
	private String password;
	private static User user;
	
	private User() {
		// TODO Auto-generated constructor stub
	}
	public static User getcUser(){
		if(user==null){
			user = new User();
		}
		return user;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
