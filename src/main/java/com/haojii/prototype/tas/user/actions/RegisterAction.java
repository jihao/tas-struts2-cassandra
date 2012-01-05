package com.haojii.prototype.tas.user.actions;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts2.util.ServletContextAware;

import com.haojii.prototype.tas.model.User;
import com.haojii.prototype.tas.services.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * the action for user registration
 * 
 * @author ehaojii
 *
 */
public class RegisterAction extends ActionSupport implements ServletContextAware {

	private static final Logger logger = Logger.getLogger(RegisterAction.class);
	
	private UserService userService;
	private ServletContext context;
	
	public RegisterAction() {
		super();
		logger.debug("constuctor RegisterAction, conext isNull?="+(context==null));
		boolean debug = Boolean.parseBoolean(context.getInitParameter("debug"));
		userService = new UserService(debug);
	}

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
		
		//save user to cassandra, simplify logic
		userService.insertUser(user);
		
		//if register succeed, redirect user to login page
		return Action.SUCCESS;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
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
