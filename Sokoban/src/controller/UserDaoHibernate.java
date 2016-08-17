package controller;

import java.util.Collections;
import java.util.List;

import model.User;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 * Class for connecting the user class to the mysql database
 * @author user
 *
 */
public class UserDaoHibernate implements UserDAO {

	private SessionFactory sessionFactory;

	/**
	 * Constructor for class
	 */
	public UserDaoHibernate() {
		 sessionFactory = HibernateUtil.getSessionFactory();
	}
	
	/**
	 * Getting the current database session
	 * @return the current session
	 */
	public Session session(){
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Returns the current user based on the name
	 * @param the name on which to search
	 */
	@Override
	public User getUser(String name) {
		session().beginTransaction();
		
		Criteria crit= session().createCriteria(User.class);
		crit.add(Restrictions.idEq(name));
		User user =(User) crit.uniqueResult();
		session().getTransaction().commit();
		return user;
	}
	
	/**
	 * Save user to database
	 * @param user to be saved
	 */
	@Override
	public void saveUser(User user) {
		session().beginTransaction();
		session().saveOrUpdate(user);
		session().getTransaction().commit();

	}
	
	/**
	 * Get all users from database
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		session().beginTransaction();
		List<User> userList = session().createQuery("from User").list();
		Collections.sort(userList);
		session().getTransaction().commit();
		return 	userList;
				
	}

}
