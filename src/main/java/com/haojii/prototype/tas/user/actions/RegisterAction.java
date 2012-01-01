package com.haojii.prototype.tas.user.actions;

import org.apache.log4j.Logger;

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

	private static final Logger logger = Logger.getLogger(RegisterAction.class);
	
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String registerUI()  {
		return Action.SUCCESS;
	}
	
	public String register() {
		logger.debug(user.toString());
		//TODO: save user to cassandra
		
		return Action.SUCCESS;
	}


	/* validation can be done by configure a xml, see RegisterAction-register-validation.xml
	@Override
	public void validate() {
		if ( user.getUsername().trim().length() == 0 ) {
			addFieldError( "user.username", "name is required." );	
		}
		super.validate();
	}
	*/

}
