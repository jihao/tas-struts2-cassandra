package com.haojii.prototype.tas.dao;

import java.util.Collection;

import com.haojii.prototype.tas.model.User;

public interface UserDAO {
	public boolean insertUser(User user);

	public boolean deleteUser(String username);

	public User findUser(String username);

	public boolean updateUser(User user);

	public Collection<User> listUsers();
	
	public int countUsers();
}
