package com.haojii.prototype.tas.services;

import java.util.Collection;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.haojii.prototype.tas.dao.UserDAO;
import com.haojii.prototype.tas.dao.UserDAOCassandraImpl;
import com.haojii.prototype.tas.model.User;

public class UserService {

	private UserDAO userDAO;
	private static Logger logger = Logger.getLogger(UserService.class);
	
	public UserService(boolean debug) {
		userDAO = (debug? null : (new UserDAOCassandraImpl()));
	}
			
	public boolean insertUser(User user) {
		return userDAO.insertUser(user);
	}

	public boolean deleteUser(String username) {
		return userDAO.deleteUser(username);
	}

	public User findUser(String username) {
		return userDAO.findUser(username);
	}

	public boolean updateUser(User user) {
		return userDAO.updateUser(user);
	}

	public Collection<User> listUsers() {
		return userDAO.listUsers();
	}
	
	public int countUsers(){
		return userDAO.countUsers();
	}
	
	public boolean verifyUsernamePassword(User user) {
		User persistUser = findUser(user.getUsername());
		if (persistUser == null) {
			return false;
		}
		String md5Password = DigestUtils.md5Hex(user.getPassword());
		logger.debug("user input : MD5(password)=" + md5Password);
		logger.debug("database   : MD5(password)=" + persistUser.getPassword());
		
		return persistUser.getPassword().equals(md5Password);
	}
}
