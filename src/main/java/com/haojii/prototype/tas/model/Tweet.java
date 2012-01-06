package com.haojii.prototype.tas.model;


public class Tweet {

	private String message;
	
	//UUID string
	private String id_key;
	
	//relations
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId_key() {
		return id_key;
	}

	public void setId_key(String id_key) {
		this.id_key = id_key;
	}

	@Override
	public String toString() {
		return "Tweet [message=" + message + ", id_key=" + id_key
				+ ", user=" + user + "]";
	}

}
