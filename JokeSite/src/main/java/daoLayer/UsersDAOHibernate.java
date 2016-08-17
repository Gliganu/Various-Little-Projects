package daoLayer;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import domainLayer.Joke;
import domainLayer.User;
import domainLayer.UserRatingComparator;

@Component("usersDao")
@Repository
@Transactional
public class UsersDAOHibernate implements UsersDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session session(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void addOrUpdateUser(User user) {
		session().saveOrUpdate(user);
	}

	@Override
	public void deleteUser(String username) {
		Criteria crit= session().createCriteria(User.class);
		crit.add(Restrictions.idEq(username));
		session().delete(crit.uniqueResult());
	}

	@Override
	public List<User> getAllUsers() {
		return session().createQuery("from User").list();
	}

	@Override
	public User getUserByUsername(String username) {
		Criteria crit= session().createCriteria(User.class);
		crit.add(Restrictions.idEq(username));
		return (User) crit.uniqueResult();
	}

	@Override
	public boolean exists(String username) {

		Criteria crit= session().createCriteria(User.class);
		crit.add(Restrictions.idEq(username));
		return ((User) crit.uniqueResult()) != null;
		
	}

	@Override
	public List<User> getUsersByRating() {
	List<User> userList = this.getAllUsers();
		
		Collections.sort(userList,new UserRatingComparator());
		
		return userList;
	}

	@Override
	public void upVote(int id) {
		Criteria crit= session().createCriteria(Joke.class);
		crit.add(Restrictions.idEq(id));
		Joke joke = (Joke) crit.uniqueResult();
		joke.getUser().upVote();
		
	}

	@Override
	public void downVote(int id) {
		Criteria crit= session().createCriteria(Joke.class);
		crit.add(Restrictions.idEq(id));
		Joke joke = (Joke) crit.uniqueResult();
		joke.getUser().downVote();
		
	}

	@Override
	public void changePassword(String username, String password) {
		Criteria crit= session().createCriteria(User.class);
		crit.add(Restrictions.idEq(username));
		User currentUser=(User) crit.uniqueResult();
		currentUser.setPassword(password);
		session().update(currentUser);
		
	}

}
