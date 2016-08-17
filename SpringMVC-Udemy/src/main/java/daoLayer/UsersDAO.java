package daoLayer;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import domainLayer.User;

public interface UsersDAO {

	public abstract void createUser(User user);

	public abstract boolean exists(String username);

	public abstract List<User> getAllUsers();

	public abstract User getUser(String username);

}