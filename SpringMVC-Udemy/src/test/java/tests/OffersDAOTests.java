package tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import daoLayer.OffersDAO;
import daoLayer.UsersDAO;
import domainLayer.Offer;
import domainLayer.User;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
		"file:src/main/java/config/security-context.xml",
		"file:src/test/java/testConfig/datasource.xml" }) 
@RunWith(SpringJUnit4ClassRunner.class)
public class OffersDAOTests {

	@Autowired
	private UsersDAO usersDAO;

	@Autowired
	private OffersDAO offersDAO;

	@Autowired
	private DataSource dataSource;
	
	private User user1 = new User("Gliga", "pass", "g@g.com", "user", true,
			"Gliga Bogdan");
	
	private User user2 = new User("Mihai", "pass", "mihai@yahoo.com", "user", false,
			"Mihai Ionescu");

	private User user3 = new User("Andreea", "pass", "andreea@google.com", "user", true,
			"Andreea Muresan");
	
	

	@Before
	public void init() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.execute("delete from offers");
		jdbcTemplate.execute("delete from messages");
		jdbcTemplate.execute("delete from users");
	}
	
	@Test
	public void testCreateRetrieve(){
		usersDAO.createUser(user1);
		usersDAO.createUser(user2);
		usersDAO.createUser(user3);
		
		Offer offer1 =new Offer(user1, "I know CSS");
		Offer offer2 =new Offer(user2, "MySQL is my specialty");
		Offer offer3 =new Offer(user3, "I am specialized in Java");
		
		offersDAO.addOffer(offer1);
		offersDAO.addOffer(offer2);
		offersDAO.addOffer(offer3);
		
		List<Offer> offerList=offersDAO.getAllOffers();
				
		assertEquals("Should retrive only offers for enabled users",2,offerList.size());
		assertEquals("User from offer should match with initial one",user1,offerList.get(0).getUser());
		
		List<Offer> offerList2=offersDAO.getOfferByUser("Andreea");
		assertEquals("Offer should match with the initial one",offer3,offerList2.get(0));

		List<Offer> offerList3=offersDAO.getOfferByUser("asdasdasdas");
		assertEquals("List should be empty ",0,offerList3.size());
		
	}
	
	@Test
	public void testUpdate(){
		usersDAO.createUser(user1);
		Offer offer1 =new Offer(user1, "I know CSS");
		offersDAO.addOffer(offer1);
		
		offer1.setText("This is the updated text");
		offersDAO.update(offer1);
		
		List<Offer> offerList=offersDAO.getAllOffers();
		assertEquals("Offer should match with the initial one",offer1,offerList.get(0));
	}
	
	@Test
	public void testDelete(){
		usersDAO.createUser(user1);
		Offer offer1 =new Offer(user1, "I know CSS");
		offersDAO.addOffer(offer1);
		offersDAO.delete(offer1.getId());
		
		List<Offer> offerList=offersDAO.getAllOffers();
		assertEquals("There should be no offers",0,offerList.size());
		
	}
	
}