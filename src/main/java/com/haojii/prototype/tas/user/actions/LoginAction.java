package com.haojii.prototype.tas.user.actions;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.haojii.prototype.tas.model.User;
import com.haojii.prototype.tas.services.ServiceFactory;
import com.haojii.prototype.tas.services.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * the action for user sign in and sign out
 * 
 * @author ehaojii
 *
 */
public class LoginAction extends ActionSupport implements ServletContextAware, SessionAware {

	private static final Logger logger = Logger.getLogger(LoginAction.class);
	
	private ServletContext context;
	private Map<String, Object> session;

	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String loginUI()  {
		return Action.SUCCESS;
	}
	
	public String login() {
		logger.debug(user.toString());
		
		UserService userService = ServiceFactory.getInstance(context).getUserService();
		boolean result = userService.verifyUsernamePassword(user);
		
		if (result) {
			logger.debug("SUCCESS");
			session.put("SESSION_CURRENT_USER", user);
			return Action.SUCCESS;
		} else {
			logger.debug("ERROR");
			addActionError("Authentication failed, please retry!");
			return Action.INPUT;
		}
	}

	public String logout()
	{
		session.remove("SESSION_CURRENT_USER");
		return Action.SUCCESS;
	}
	
	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	@Override
	public void validate() {
		if ( (user!=null) && (user.getUsername().trim().length() == 0 )) {
			addFieldError( "user.username", "name is required." );	
		}
		if ( (user!=null) && (user.getPassword().trim().length() == 0 )) {
			addFieldError( "user.password", "password is required." );	
		}
		super.validate();
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
