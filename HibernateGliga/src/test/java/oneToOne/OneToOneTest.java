package oneToOne;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.HibernateUtil;

public class OneToOneTest {

	private SessionFactory sf = HibernateUtil.getSessionFactory();
	private Session session;
	
	@Before
	public void setUp() {
		session = sf.openSession();
	}
	
	@After
	public void tearDown() {
		session.close();
	}
	
	@Test
	public void addEmployeesAndMeetings() {
		
		Book book=new Book();
		book.setName("Luceafarul");
		
		Author author=new Author();
		author.setName("Eminescu");
		
		book.setAuthor(author);
		author.setBook(book);
		
		Book book2=new Book();
		book2.setName("Enigma Otiliei");
		
		Author author2=new Author();
		author2.setName("Calinescu");
		
		book2.setAuthor(author2);
		author2.setBook(book2);
		
		
		
		session.beginTransaction();
		
		session.save(author);
		session.save(book);
		
		session.save(author2);
		session.save(book2);
		session.getTransaction().commit();
	}
	
	/*
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
