package com.haojii.prototype.tas.model;

import java.util.Date;

public class Tweet {

	private String message;
	private String id_key;
	private Date created_at = new Date();
	
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

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Tweet [message=" + message + ", id_key=" + id_key
				+ ", created_at=" + created_at + ", user=" + user + "]";
	}

}
