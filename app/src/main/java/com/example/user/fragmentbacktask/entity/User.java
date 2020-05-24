package com.example.user.fragmentbacktask.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@SerializedName("username")
	public String username ;
	@SerializedName("password")
	public String password  ;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString()
	{
		return "{\"username\":"+username +"\"password\":"+password +"}";
	}
}
