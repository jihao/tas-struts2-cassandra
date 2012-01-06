package com.haojii.prototype.tas.services;

import javax.servlet.ServletContext;

public class ServiceFactory {

	private boolean debug;
	private static ServiceFactory _instance;
	
	private ServiceFactory(boolean debug) {
		super();
		this.debug = debug;
	}
	
	public synchronized static ServiceFactory getInstance(ServletContext context) {
		if(_instance == null) {
			boolean debug = Boolean.parseBoolean(context.getInitParameter("debug"));
			_instance = new ServiceFactory(debug);
		}
		return _instance;
	}

	public UserService getUserService() {
		return new UserService(this.debug);
	}

}
