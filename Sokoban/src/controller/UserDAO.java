package controller;

import java.util.List;

import model.User;

/**
 * Interface for defining database operations
 * @author user
 *
 */
public interface UserDAO {

	public abstract User getUser(String name);

	public abstract void saveUser(User user);
	
	public abstract List<User> getAllUsers();

}