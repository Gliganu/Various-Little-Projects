package daoLayer;

import java.util.List;

import domainLayer.User;

public interface UsersDAO {

	public void addOrUpdateUser(User user);
	
	public void deleteUser(String username);

	public List<User> getAllUsers();
	
	public User getUserByUsername(String username);
	
	public boolean exists(String username);
	
	public List<User> getUsersByRating();

	public void upVote(int id);

	public void downVote(int id);

	public void changePassword(String username, String password);
	
}
