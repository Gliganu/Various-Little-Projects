package serviceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daoLayer.UsersDAO;
import domainLayer.User;

@Service("usersService")
public class UsersService{

	@Autowired
	private UsersDAO usersDao;
	
	public void addOrUpdateUser(User user) {
		usersDao.addOrUpdateUser(user);
	}

	public void deleteUser(String username) {
		usersDao.deleteUser(username);
	}

	public List<User> getAllUsers() {
		return usersDao.getAllUsers();
	}

	public User getUserByUsername(String username) {
		return usersDao.getUserByUsername(username);
	}

	public boolean exists(String username) {
		return usersDao.exists(username);
	}

	public List<User> getUsersByRating() {
		return usersDao.getUsersByRating();
	}

	public void upVote(int id) {
		usersDao.upVote(id);
	}

	public void downVote(int id) {
		usersDao.downVote(id);
	}

	public void changePassword(String username, String password) {
		usersDao.changePassword(username,password);
	}

	
}
