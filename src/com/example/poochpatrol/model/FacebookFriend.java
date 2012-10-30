package com.example.poochpatrol.model;


public class FacebookFriend{
	
	private int id;
	private String name;
	
	public FacebookFriend(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
}
