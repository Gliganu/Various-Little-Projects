package oneToMany;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.HibernateUtil;

public class OneToManyTest {

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
		
		Basket basket=new Basket();
		basket.setName("Cosulet");
		
		Fruit fruit=new Fruit("Apple", "Red");
		Fruit fruit2=new Fruit("Banan", "Yellow");
		
		fruit.setBasket(basket);
		fruit2.setBasket(basket);
		
		List<Fruit> fruitList=new ArrayList<>();
		fruitList.add(fruit);
		fruitList.add(fruit2);
		
		basket.setFruitList(fruitList);
		
		session.beginTransaction();
		
		session.save(basket);
		session.save(fruit);
		session.save(fruit2);
		
		session.getTransaction().commit();
	}
	
	
}
