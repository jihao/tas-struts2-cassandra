package com.haojii.prototype.tas.user.actions;

import com.haojii.prototype.tas.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * the action for user registration
 * 
 * @author ehaojii
 *
 */
public class RegisterAction extends ActionSupport {

	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String execute() throws Exception {
		user = new User("testuser1","testuser1");
		
		return Action.SUCCESS;
	}

}
