package hibernateTests;

import model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.UserDAO;
import controller.UserDaoHibernate;

public class HibernateTest {

	private UserDAO userDAO;
	private User user1 = new User("Bogdan", 100, 2);
	private User user2 =new User("Mihai", 1234, 4);
	private User user3 =new User("Andrei", 11, 1);
	private User user4 =new User("Mircea", 123, 3);
	
	
	@Before
	public void setUp() {
		userDAO = new UserDaoHibernate();
	}
	
	
	/*
	@Test
	public void addUsers() {
		
		userDAO.saveUser(user3);
		userDAO.saveUser(user4);
		
	}
	
	
	//@Test
	public void testEmployeeMeetingsNumber() {
		Employee employee = (Employee) session.createQuery("from Employee where LASTNAME = 'Brin'").uniqueResult();
		assertTrue(employee.getMeetings().size() == 2);
	}
	
	//@Test
	public void updateMeetingThroughEmployee() {
		Employee employee = (Employee) session.createQuery("from Employee where LASTNAME = 'Page'").uniqueResult();
		
		employee.setFirstname("updated " + employee.getFirstname());
		Set<Meeting> meetings = employee.getMeetings();
		for(Meeting m : meetings) {
			m.setSubject("updated " + m.getSubject());
		}
		
		session.beginTransaction();
		session.saveOrUpdate(employee); // see session.update()
		session.getTransaction().commit();
	}
	
	@OneToOneTest
	public void deleteMeeting() {
		Meeting meeting = (Meeting) session.createQuery("from Meeting where SUBJECT = 'Annual meeting'").uniqueResult();
		
		session.beginTransaction();
		session.delete(meeting);
		session.getTransaction().commit();
	}*/
	
}
