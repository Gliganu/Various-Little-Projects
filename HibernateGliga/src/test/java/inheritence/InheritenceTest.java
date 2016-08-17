package inheritence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.HibernateUtil;

public class InheritenceTest {

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
	public void addPersons() {
	
		Person person=new Person("Bogdan");
		Person person2=new Person("Andrei");
		
		Employee employee=new Employee("Bob", "IT");
		
		Manager manager=new Manager("Richard", "CEO");
		Manager manager2=new Manager("Mark", "CFO");
		
		session.beginTransaction();
		
		session.save(person);
		session.save(person2);
		session.save(employee);
		session.save(manager);
		session.save(manager2);
		
		session.getTransaction().commit();
	}
	
	
}
